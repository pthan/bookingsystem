package com.my.bookingsystem.user.service;

import com.my.bookingsystem.user.dto.request.LoginRequest;
import com.my.bookingsystem.domain.response.ResponseFormat;

public interface AuthService {
    public ResponseFormat authenticateUser(LoginRequest loginRequest);
}
