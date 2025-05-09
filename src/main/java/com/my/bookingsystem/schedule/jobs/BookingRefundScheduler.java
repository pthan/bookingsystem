package com.my.bookingsystem.schedule.jobs;

import com.my.bookingsystem.config.Constants;
import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import com.my.bookingsystem.purchasepackage.repository.PurchaseCreditPackageRepository;
import com.my.bookingsystem.schedule.entity.Booking;
import com.my.bookingsystem.schedule.entity.BookingPackageUsage;
import com.my.bookingsystem.schedule.repository.BookingRepository;
import com.my.bookingsystem.schedule.repository.ClassScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

import com.my.bookingsystem.schedule.entity.ClassSchedule;

import static com.my.bookingsystem.config.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingRefundScheduler {

    private final ClassScheduleRepository classScheduleRepository;
    private final BookingRepository bookingRepository;
    private final PurchaseCreditPackageRepository packageRepository;

    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    @Transactional
    public void expireClassRefundProcessForWaitlistUsers() {

        ZonedDateTime now = ZonedDateTime.now();
        log.info("Expire Refund checking process start at "+now.toString());
        List<ClassSchedule> endedClasses = classScheduleRepository.findAllByEndDateBeforeAndScheduleClassStatus(
                now, STATUS_PROGRESS);

        for (ClassSchedule schedule : endedClasses) {
            List<Booking> waitlist = bookingRepository
                    .findAllByScheduleAndBookingStatusAndPaymentStatus(
                            schedule,
                            BOOKING_WAITING_STATUS,
                            PAYMENT_HOLD_STATUS
                    );

            for (Booking booking : waitlist) {
                int refundAmount = 0;

                for (BookingPackageUsage usage : booking.getPackageUsages()) {
                    PurchaseCreditPackage pkg = usage.getCreditPackage();
                    pkg.setRemainingCredit(pkg.getRemainingCredit() + usage.getUsedCredit());
                    refundAmount += usage.getUsedCredit();
                    packageRepository.save(pkg);
                }

                booking.setPaymentStatus(PAYMENT_REFUND_STATUS);
                booking.setUpdatedOn(ZonedDateTime.now());
                bookingRepository.save(booking);

                log.info(" Refunded {} credit(s) to user ID {}", refundAmount, booking.getUser().getId());
            }

            schedule.setScheduleClassStatus(STATUS_COMPLETE);
            classScheduleRepository.save(schedule);
        }
    }
}

