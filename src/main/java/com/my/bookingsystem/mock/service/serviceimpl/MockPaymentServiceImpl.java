package com.my.bookingsystem.mock.service.serviceimpl;

import com.my.bookingsystem.mock.service.MockPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockPaymentServiceImpl implements MockPaymentService {
    @Override
    public boolean addPaymentCard(String cardNumber, String cvc, String expiryDate) {

        boolean b= isValidCard(cardNumber) && isValidCVC(cvc) && isValidExpiryDate(expiryDate);
        if(b){
            log.info("adding card success!");
        }
        else {
            log.info("Invalid Card number !");
        }
        return b;
    }
    public boolean chargePayment(String userId, double amount) {
        if (amount <= 0) {
            log.info("Invalid Card Charge Amount!");
            return  false;
        }
        return true;
    }


    private boolean isValidCard(String number) {
        return number != null && number.matches("\\d{16}");
    }

    private boolean isValidCVC(String cvc) {
        return cvc != null && cvc.matches("\\d{3}");
    }

    private boolean isValidExpiryDate(String expiry) {
        return expiry != null && expiry.matches("^(0[1-9]|1[0-2])/\\d{2}$");
    }
}
