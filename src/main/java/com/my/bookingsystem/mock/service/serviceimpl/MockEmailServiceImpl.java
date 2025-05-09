package com.my.bookingsystem.mock.service.serviceimpl;

import com.my.bookingsystem.mock.service.MockEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockEmailServiceImpl implements MockEmailService {

    public boolean sendVerificationEmail(String to, String name, String subject ,String content) {
        if (to == null || to.isBlank() || !isValidEmail(to) || content==null || content.isBlank()) {
            return false;
        }

        log.info("Sent verification email to {} for user {} with subject {} and content {}", to, name,subject,content);
        return true;
    }
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
