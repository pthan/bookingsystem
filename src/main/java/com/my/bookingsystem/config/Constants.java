package com.my.bookingsystem.config;

public class Constants {
    public static  String STATUS_ACTIVE="Active";
    public  static  String STATUS_IN_ACTIVE="Inactive";
    public  static  String STATUS_PROGRESS="Progress";
    public  static  String STATUS_COMPLETE="Complete";

    //booking setting
    public  static  String BOOKING_SUCCESS_STATUS="BOOKED";
    public  static  String BOOKING_WAITING_STATUS="WAITING";
    public  static  String BOOKING_DECLINE_STATUS="DECLINE";
    public  static  String BOOKING_CANCEL_STATUS="CANCEL";
    public  static  String PAYMENT_PAID_STATUS="PAID";
    public  static  String PAYMENT_HOLD_STATUS="HOLD";
    public  static  String PAYMENT_REFUND_STATUS="REFUND";

    //concurrent setting
    public  static  int LOCK_TIME=5;
    public static  int LEASE_TIME=10;
    public  static int CONCURRENT_USER=5;
    public static final String LOCK_KEY_PREFIX = "booking-lock-";
    public static final String COUNT_KEY_PREFIX = "booking-active-counter:";
}
