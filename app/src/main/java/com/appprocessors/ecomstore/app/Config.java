package com.appprocessors.ecomstore.app;

/**
 * Created by Ravi on 08/07/15.
 */
public class Config {
    // server URL configuration
    public static final String URL_REQUEST_SMS = "http://172.20.10.4/android_sms/include/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://172.20.10.4/android_sms/include/verify_otp.php";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "ESTORE";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
}
