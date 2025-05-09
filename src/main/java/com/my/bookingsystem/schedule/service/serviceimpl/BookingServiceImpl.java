package com.my.bookingsystem.schedule.service.serviceimpl;

import com.my.bookingsystem.config.Constants;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import com.my.bookingsystem.purchasepackage.repository.PurchaseCreditPackageRepository;
import com.my.bookingsystem.schedule.dto.response.BookingResponse;
import com.my.bookingsystem.schedule.entity.Booking;
import com.my.bookingsystem.schedule.entity.BookingPackageUsage;
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
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.my.bookingsystem.config.Constants.CONCURRENT_USER;
import static com.my.bookingsystem.config.Constants.COUNT_KEY_PREFIX;

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

    @Override
    public ResponseFormat  makeBooking(Long scheduleId, Long userId) {
        ResponseFormat responseFormat=null;
        String errorMessage="";
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
            errorMessage="Insufficient credits across all packages.";
            throw new BusinessException(errorMessage);
        }

        if (schedule.getBookingCount() >= schedule.getAvailableSlots()) {
            errorMessage="Class is full. You should add in waitlist.";
            throw new BusinessException(errorMessage);
        }

        Booking booking = new Booking();
        booking.setGuid(UUID.randomUUID().toString());
        booking.setSchedule(schedule);
        booking.setUser(user);
        booking.setCredit(requiredCredit);
        booking.setCreatedBy(userId);
        booking.setBookingStatus(Constants.BOOKING_SUCCESS_STATUS);
        booking.setStatus(Constants.STATUS_ACTIVE);
        booking.setCreatedOn(ZonedDateTime.now());

        int remainingToDeduct = requiredCredit;
        List<BookingPackageUsage> usageList = new ArrayList<>();

        for (PurchaseCreditPackage pkg : packages) {
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

        }catch (Exception e){
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data(errorMessage)
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
}
