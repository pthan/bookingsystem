package com.my.bookingsystem.config;

public class Constants {
    public static  String STATUS_ACTIVE="Active";
    public  static  String STATUS_IN_ACTIVE="Inactive";

    //booking setting
    public  static  String BOOKING_SUCCESS_STATUS="Success";
    public  static  String BOOKING_PENDING_STATUS="Pending";
    public  static  String BOOKING_DECLINE_STATUS="Decline";

    //concurrent setting
    public  static  int LOCK_TIME=5;
    public static  int LEASE_TIME=10;
    public  static int CONCURRENT_USER=5;
    public static final String LOCK_KEY_PREFIX = "booking-lock-";
    public static final String COUNT_KEY_PREFIX = "booking-active-counter:";
}
