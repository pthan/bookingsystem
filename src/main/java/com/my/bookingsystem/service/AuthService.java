package com.my.bookingsystem.service;

import com.my.bookingsystem.model.request.LoginRequest;
import com.my.bookingsystem.model.response.ResponseFormat;

public interface AuthService {
    public ResponseFormat authenticateUser(LoginRequest loginRequest);
}
