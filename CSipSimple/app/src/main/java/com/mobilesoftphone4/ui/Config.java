package com.mobilesoftphone4.ui;

/**
 * Created by Sharad on 10/09/2016.
 */
public class Config {
    //URLs to register.php and confirm.php file
	
	 public static final String REGISTER_URL = "https://billing.yepingo.co.uk/web/otp_send/show_otp.php?phone=";
   // public static final String REGISTER_URL = "https://admin.cloufon.de/php/otp.php?phone=91";
    //public static final String CONFIRM_URL = "https://billing.yepingo.co.uk/web/url_out.php?phone="+&otp=59660";

    //Keys to send username, password, phone and otp
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_OTP = "otp";
  
   // public static final String CONFIRM_URL = "https://admin.cloufon.de/php/otp.php?phone=91";
    
    public static final String CONFIRM_URL = "https://billing.yepingo.co.uk/web/otprs1.php?phone=";
    //JSON Tag from response from serverhttps://billing.yepingo.co.uk/web/otprs1.php?phone=09899054328&otp=
    public static final String TAG_RESPONSE= "ErrorMessage";
}
