package com.my.bookingsystem.mock.service;

public interface MockPaymentService {
    public boolean addPaymentCard(String cardNumber, String cvc, String expiryDate);
    public boolean chargePayment(String userId, double amount);

}
