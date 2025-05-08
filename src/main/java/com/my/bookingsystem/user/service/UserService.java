package com.my.bookingsystem.user.service;

import com.my.bookingsystem.user.dto.request.UserRequest;
import com.my.bookingsystem.domain.response.ResponseFormat;

public interface UserService {
    public ResponseFormat saveUser(UserRequest userRequest , String type);
}
