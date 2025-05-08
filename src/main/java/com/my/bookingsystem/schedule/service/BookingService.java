package com.my.bookingsystem.schedule.service;

import com.my.bookingsystem.domain.response.ResponseFormat;
import jakarta.transaction.Transactional;

public interface BookingService {
    @Transactional
    ResponseFormat makeBooking(Long scheduleId, Long userId);

}
