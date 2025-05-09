package com.my.bookingsystem.mock;

import com.my.bookingsystem.mock.service.MockPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MockPaymentServiceTest {
    @Autowired
    private MockPaymentService paymentService;

    @Test
    void shouldReturnTrueForValidCardInfo() {
        assertTrue(paymentService.addPaymentCard("4111222233334444", "123", "12/25"));
    }

    @Test
    void shouldReturnFalseForInvalidCardNumber() {
        assertFalse(paymentService.addPaymentCard("4111", "123", "12/25"));
    }

    @Test
    void shouldReturnFalseForInvalidCVC() {
        assertFalse(paymentService.addPaymentCard("4111222233334444", "12a", "12/25"));
    }

    @Test
    void shouldReturnFalseForInvalidExpiry() {
        assertFalse(paymentService.addPaymentCard("4111222233334444", "123", "2025"));
    }

    @Test
    void shouldThrowForNegativeChargeAmount() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.chargePayment("user123", -50));
    }

    @Test
    void shouldSucceedOnValidCharge() {
        assertTrue(paymentService.chargePayment("user123", 25.0));
    }
}
