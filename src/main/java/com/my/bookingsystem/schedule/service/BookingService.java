package com.my.bookingsystem.schedule.service;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.schedule.dto.request.BookingUserSearchRequest;
import jakarta.transaction.Transactional;

public interface BookingService {
    @Transactional
    ResponseFormat makeBooking(Long scheduleId, Long userId);
    @Transactional
    ResponseFormat joinWaitlist(Long scheduleId, Long userId);
    @Transactional
    ResponseFormat cancelBooking(Long bookingId, Long userId);

    ResponseFormat userBookingList(BookingUserSearchRequest request,long userId);

}
