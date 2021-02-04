package com.sysoiev.developers_db.service;

public interface PhoneVerificationService {

    void sendCodeSms(String phoneNumber);

    boolean verifyCode(String phoneNumber, String code);
}
