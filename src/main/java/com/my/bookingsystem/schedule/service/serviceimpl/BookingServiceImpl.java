package com.my.bookingsystem.schedule.service.serviceimpl;

import com.my.bookingsystem.config.Constants;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.mapper.ScheduleMapper;
import com.my.bookingsystem.mock.service.MockEmailService;
import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import com.my.bookingsystem.purchasepackage.repository.PurchaseCreditPackageRepository;
import com.my.bookingsystem.schedule.dto.request.BookingUserSearchRequest;
import com.my.bookingsystem.schedule.dto.response.*;
import com.my.bookingsystem.schedule.entity.Booking;
import com.my.bookingsystem.schedule.entity.BookingPackageUsage;
import com.my.bookingsystem.schedule.entity.ClassInfo;
import com.my.bookingsystem.schedule.entity.ClassSchedule;
import com.my.bookingsystem.schedule.repository.BookingPackageUsageRepository;
import com.my.bookingsystem.schedule.repository.BookingRepository;
import com.my.bookingsystem.schedule.repository.ClassScheduleRepository;
import com.my.bookingsystem.schedule.service.BookingService;
import com.my.bookingsystem.shared.exceptions.BusinessException;
import com.my.bookingsystem.user.entity.User;
import com.my.bookingsystem.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.my.bookingsystem.config.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {


    private final ClassScheduleRepository classScheduleRepo;
    private final PurchaseCreditPackageRepository packageRepo;
    private final BookingRepository bookingRepo;
    private final BookingPackageUsageRepository usageRepo;
    private final UserRepository userRepository;
    private  final  RedissonClient redissonClient;
    private final ScheduleMapper scheduleMapper;

    @Override
    public ResponseFormat  makeBooking(Long scheduleId, Long userId) {
        ResponseFormat responseFormat=null;
        String lockKey =Constants.LOCK_KEY_PREFIX+ scheduleId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean locked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!locked) {
                throw new BusinessException("System busy. Try again.");
            }
            ClassSchedule schedule = classScheduleRepo.findById(scheduleId)
                    .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

            int effectiveConcurrency = Math.min(CONCURRENT_USER, schedule.getAvailableSlots());

            RAtomicLong counter = redissonClient.getAtomicLong(COUNT_KEY_PREFIX + scheduleId);
            if (counter.incrementAndGet() > effectiveConcurrency) {
                counter.decrementAndGet();
                throw new BusinessException("Class is full.");
            }
        try{
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        int requiredCredit = schedule.getClassInfo().getRequiredCredit();
        Long classCountryId = schedule.getClassInfo().getCountry().getId();

        List<PurchaseCreditPackage> packages = packageRepo
                .findValidPackagesByUserAndCountry(
                        userId, classCountryId, Constants.STATUS_ACTIVE, ZonedDateTime.now());

        int totalAvailable = packages.stream().mapToInt(PurchaseCreditPackage::getRemainingCredit).sum();
        if (totalAvailable < requiredCredit) {
            throw new BusinessException("Insufficient credits across all packages.");
        }

        if (schedule.getBookingCount() >= schedule.getAvailableSlots()) {
            throw new BusinessException("Class is full. You should add in waitlist.");
        }

        Booking booking = new Booking();
        booking.setGuid(UUID.randomUUID().toString());
        booking.setSchedule(schedule);
        booking.setUser(user);
        booking.setPaymentStatus(PAYMENT_PAID_STATUS);
        booking.setCredit(requiredCredit);
        booking.setCreatedBy(userId);
        booking.setBookingStatus(Constants.BOOKING_SUCCESS_STATUS);
        booking.setStatus(Constants.STATUS_ACTIVE);
        booking.setCreatedOn(ZonedDateTime.now());

        List<BookingPackageUsage> usageList = getPackageUsage(requiredCredit,packages,booking);

        booking.setPackageUsages(usageList);
            bookingRepo.save(booking);
        usageRepo.saveAll(usageList);

        packageRepo.saveAll(packages);
        schedule.setBookingCount(schedule.getBookingCount() + 1);
        classScheduleRepo.save(schedule);

            Booking fullBooking = bookingRepo.findByIdWithPackages(booking.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

            BookingResponse response = mapToBookingResponse(fullBooking);
        responseFormat=new ResponseFormat();
        responseFormat.setSuccess(true);
        responseFormat.setMessage(Optional.of("Make Booking success!"));
        responseFormat.setData(Optional.of(response));

        }catch (BusinessException e){
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data(e.getMessage())
                    .build();
        }
        finally {
            counter.decrementAndGet();
        }

        } catch (InterruptedException e) {

        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }





        return responseFormat;
    }
    @Transactional
    public ResponseFormat joinWaitlist(Long scheduleId, Long userId) {
        ResponseFormat responseFormat=null;
        try{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ClassSchedule schedule = classScheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        boolean alreadyBooked = bookingRepo.existsByScheduleAndUser(schedule, user);
        if (alreadyBooked) {
            throw new BusinessException("You are already booked or waitlisted for this class.");
        }

        int requiredCredit = schedule.getClassInfo().getRequiredCredit();
        Long classCountryId = schedule.getClassInfo().getCountry().getId();

        List<PurchaseCreditPackage> packages = packageRepo.findValidPackagesByUserAndCountry(
                userId, classCountryId, Constants.STATUS_ACTIVE, ZonedDateTime.now());

        int totalAvailable = packages.stream().mapToInt(PurchaseCreditPackage::getRemainingCredit).sum();
        if (totalAvailable < requiredCredit) {
            throw new BusinessException("Insufficient credits across all packages.");
        }

        // Deduct credits and track usage
        Booking booking = new Booking();
        booking.setGuid(UUID.randomUUID().toString());
        booking.setSchedule(schedule);
        booking.setUser(user);
        booking.setCredit(requiredCredit);
        booking.setCreatedBy(userId);
        booking.setPaymentStatus(PAYMENT_HOLD_STATUS);
        booking.setBookingStatus(BOOKING_WAITING_STATUS);
        booking.setStatus(Constants.STATUS_ACTIVE);
        booking.setCreatedOn(ZonedDateTime.now());

        List<BookingPackageUsage> usageList = getPackageUsage(requiredCredit,packages,booking);

        booking.setPackageUsages(usageList);
        bookingRepo.save(booking);
        usageRepo.saveAll(usageList);
        packageRepo.saveAll(packages);

        schedule.setWaitingCount(schedule.getWaitingCount() + 1);
        classScheduleRepo.save(schedule);
            BookingResponse response = mapToBookingResponse(booking);
            responseFormat=new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage(Optional.of("You have successfully joined the waiting list!"));
            responseFormat.setData(Optional.of(response));
        } catch (BusinessException e) {
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data(e.getMessage())
                    .build();
        }
        return responseFormat;
    }

    @Transactional
    public ResponseFormat cancelBooking(Long bookingId, Long userId) {
        ResponseFormat responseFormat=null;
        try{
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new BusinessException("User not found"));

            Booking booking = bookingRepo.findByIdWithPackages(bookingId)
                    .orElseThrow(() -> new BusinessException("Booking not found"));

            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime classStartTime = booking.getSchedule().getStartDate(); // you must add this field

            if (now.isAfter(classStartTime.minusHours(4))) {
                throw new BusinessException("Cannot cancel within 4 hours of class start time.");
            }

            // Refund logic
            int refundAmount=0;
            List<BookingPackageUsage> usageList = booking.getPackageUsages();
            for (BookingPackageUsage usage : usageList) {
                refundAmount+=usage.getUsedCredit();
                PurchaseCreditPackage pkg = usage.getCreditPackage();
                pkg.setRemainingCredit(pkg.getRemainingCredit() + usage.getUsedCredit());
                packageRepo.save(pkg);
            }

            booking.setStatus(STATUS_ACTIVE);
            booking.setBookingStatus(BOOKING_CANCEL_STATUS);
            booking.setUpdatedBy(userId);
            booking.setUpdatedOn(ZonedDateTime.now());
            bookingRepo.save(booking);
            //reduce booking count
            ClassSchedule schedule = booking.getSchedule();
            schedule.setBookingCount(schedule.getBookingCount() - 1);

            //promote waiting list to booking list
            promoteWaitingListToBookingList(schedule,userId,user.getName());

            classScheduleRepo.save(schedule); // save updates to schedule counts
            responseFormat=new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage(Optional.of("You have successfully cancel the class and refund credit "+refundAmount+" give back to your account!"));
        }catch(BusinessException e){
            return ResponseFormat
                    .failedResponse()
                    .message(e.getMessage())
                    .build();
        }
        return responseFormat;
    }

    public  void promoteWaitingListToBookingList(ClassSchedule schedule ,Long userId,String userName){
        log.info("start pormote from user booking status waiting to book");
        Optional<Booking> waitlistEntryOpt = bookingRepo
                .findFirstByScheduleAndBookingStatusOrderByCreatedOnAsc(schedule, BOOKING_WAITING_STATUS);

        if (waitlistEntryOpt.isPresent()) {
            Booking waitlistBooking = waitlistEntryOpt.get();
            waitlistBooking.setBookingStatus(BOOKING_SUCCESS_STATUS);
            waitlistBooking.setUpdatedOn(ZonedDateTime.now());
            waitlistBooking.setUpdatedBy(userId);
            bookingRepo.save(waitlistBooking);

            schedule.setBookingCount(schedule.getBookingCount() + 1);
            schedule.setWaitingCount(schedule.getWaitingCount() - 1);

            log.info("User Id {} , name{} promote from waiting to booking  ",waitlistBooking.getUser().getId(),waitlistBooking.getUser().getName());
        }

    }

    @Override
    public ResponseFormat userBookingList(BookingUserSearchRequest request ,long userId) {
        ResponseFormat responseFormat;
        try {
            BookingListResponse response = BookingListResponse.builder()
                    .items(new ArrayList<>())
                    .totalRecords(0)
                    .build();

            int page = request.getFirst() == null ? 0 : request.getFirst();
            int size = request.getMax() == null ? Integer.MAX_VALUE : request.getMax();
            String orderBy = request.getOrderBy() == null ? "createdOn" : request.getOrderBy();
            boolean asc = Boolean.TRUE.equals(request.getAsc());

            Pageable pageable = PageRequest.of(page, size, asc
                    ? Sort.by(orderBy).ascending()
                    : Sort.by(orderBy).descending());

            Specification<Booking> spec = Specification.where((root, query, cb) -> cb.equal(root.get("user").get("id"), userId));


            boolean hasKeyword = request.getKeyword() != null && !request.getKeyword().isBlank();


            // Apply additional filters only if provided
            if (hasKeyword) {
                String pattern = "%" + request.getKeyword().toLowerCase() + "%";
                spec = spec.and((root, query, cb) ->
                        cb.like(cb.lower(root.get("classInfo").get("className")), pattern));
            }

            Page<Booking> pageResult = bookingRepo.findAll(spec, pageable);
            List<BookingItemResponse> items = pageResult.getContent().stream()
                    .filter(b -> b.getSchedule() != null)
                    .map(this::mapToBookingItemResponse)
                    .toList();

            responseFormat = new ResponseFormat();
            responseFormat.setSuccess(true);
            response.setItems(items);
            response.setTotalRecords(pageResult.getTotalElements());
            responseFormat.setMessage(Optional.of("Class schedule list successful"));
            responseFormat.setData(Optional.of(response));

            log.info("Successfully fetched schedule list, total records: {}", pageResult.getTotalElements());
        } catch (Exception e) {
            log.error("Error at fetching records", e);
            return ResponseFormat.failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }

        return responseFormat;
    }


    private  List<BookingPackageUsage>  getPackageUsage(int remainingToDeduct,List<PurchaseCreditPackage> myPackage ,Booking booking){
        List<BookingPackageUsage> usageList = new ArrayList<>();
        for (PurchaseCreditPackage pkg : myPackage) {
            if (remainingToDeduct <= 0) break;
            int deduct = Math.min(pkg.getRemainingCredit(), remainingToDeduct);
            pkg.setRemainingCredit(pkg.getRemainingCredit() - deduct);
            remainingToDeduct -= deduct;

            BookingPackageUsage usage = new BookingPackageUsage();
            usage.setBooking(booking);
            usage.setCreditPackage(pkg);
            usage.setUsedCredit(deduct);
            usage.setCountryId(pkg.getCreditPackage().getCountry().getId());
            usageList.add(usage);
        }
        return  usageList;
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setStatus(booking.getBookingStatus());
        response.setTotalCreditUsed(booking.getCredit());

        List<BookingResponse.UsedPackageDetail> usageDetails = new ArrayList<>();

        for (BookingPackageUsage usage : booking.getPackageUsages()) {
            BookingResponse.UsedPackageDetail detail = new BookingResponse.UsedPackageDetail();
            detail.setPackageId(usage.getCreditPackage() != null ? usage.getCreditPackage().getId() : null);
            detail.setUsedCredit(usage.getUsedCredit());
            detail.setCountryId(usage.getCountryId());
            usageDetails.add(detail);
        }

        response.setUsedPackages(usageDetails);
        return response;
    }
    private BookingItemResponse mapToBookingItemResponse(Booking booking) {
        ClassSchedule schedule = booking.getSchedule();
        ClassInfo info = schedule.getClassInfo();

        BookingItemResponse response = new BookingItemResponse();
        response.setBookingId(booking.getId());
        response.setBookingDate(booking.getCreatedOn().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        response.setClassName(info.getClassName());
        response.setClassScheduleId(schedule.getId());
        response.setStartDateTime(schedule.getStartDate());
        response.setCreditAmount(booking.getCredit());

        List<ClassScheduleDetailItem> scheduleDetails = schedule.getDetails().stream().map(d -> {
            ClassScheduleDetailItem item = new ClassScheduleDetailItem();
            item.setSessionDay(d.getSessionDay());
            item.setSessionStartTime(d.getSessionStartTime());
            item.setSessionEndTime(d.getSessionEndTime());
            return item;
        }).distinct().toList();
        response.setScheduleDetail(scheduleDetails);

        return response;
    }
}
