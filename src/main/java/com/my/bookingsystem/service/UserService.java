package com.my.bookingsystem.service;

import com.my.bookingsystem.model.request.UserRequest;
import com.my.bookingsystem.model.response.ResponseFormat;

public interface UserService {
    public ResponseFormat saveUser(UserRequest userRequest);
}
