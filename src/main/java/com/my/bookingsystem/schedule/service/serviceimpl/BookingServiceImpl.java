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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ClassScheduleRepository classScheduleRepo;
    private final PurchaseCreditPackageRepository packageRepo;
    private final BookingRepository bookingRepo;
    private final BookingPackageUsageRepository usageRepo;
    private final UserRepository userRepository;

    @Override
    public ResponseFormat  makeBooking(Long scheduleId, Long userId) {
        ResponseFormat responseFormat=null;
        try{
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        ClassSchedule schedule = classScheduleRepo.findById(scheduleId)
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
            throw new BusinessException("Class is full. You’ve been added to waitlist.");
        }

        Booking booking = new Booking();
        booking.setGuid(UUID.randomUUID().toString());
        booking.setSchedule(schedule);
        booking.setUser(user);
        booking.setCredit(requiredCredit);
        booking.setCreatedBy(userId);
        booking.setBookingStatus("Success");
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
            Booking fullBooking = bookingRepo.findByIdWithPackages(booking.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

// ✅ Now safe to map with mapper
            BookingResponse response = mapToBookingResponse(fullBooking);
     //   BookingResponse response = bookingMapper.toBookingResponse(booking);
        responseFormat=new ResponseFormat();
        responseFormat.setSuccess(true);
        responseFormat.setMessage(Optional.of("Make Booking success!"));
        responseFormat.setData(Optional.of(response));
        }catch (Exception e){
            e.printStackTrace();
          log.info("Error at processing ",e.getMessage());
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }
        return responseFormat;
    }
    private BookingResponse mapToBookingResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());

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
