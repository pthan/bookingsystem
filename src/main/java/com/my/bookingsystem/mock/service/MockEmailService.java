package com.my.bookingsystem.mock.service;

public interface MockEmailService {
    public boolean sendVerificationEmail(String to, String name,String subject ,String content);

}
