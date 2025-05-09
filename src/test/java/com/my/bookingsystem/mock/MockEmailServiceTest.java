package com.my.bookingsystem.mock;

import com.my.bookingsystem.mock.service.MockEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MockEmailServiceTest {

    @Autowired
    private MockEmailService emailService;

    @Test
    void shouldReturnTrueForValidEmail() {
        assertTrue(emailService.sendVerificationEmail("user@example.com", "Test User","Verify registration","registration success!"));
    }

    @Test
    void shouldReturnFalseForInvalidEmail() {
        assertFalse(emailService.sendVerificationEmail("invalid-email", "User","",null));
    }

    @Test
    void shouldReturnFalseForNullEmail() {
        assertFalse(emailService.sendVerificationEmail(null, "User",null ,null));
    }
}