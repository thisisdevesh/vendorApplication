package com.gts.coordinator.utils;

/**
 * Created by GTS Developer on 01-Mar-2017 @ 12:28
 */

public interface AppConstants {
  public static final int BOOKING_ALERT_TIMEOUT_DEFAULT_VALUE = 30 ; // seconds
  public static final String BOOKING_ALERT_TIMEOUT_KEY = "alert_time_out" ; // seconds
  public static final String KEY_BOOKING_REQUEST_DATA = "key_book_request_data" ; // seconds
  int CAB_STATUS_COUNT=4 ;
  byte CAB_STATUS_FREE=1,CAB_STATUS_BUSY=2,CAB_STATUS_LOGOUT=3,CAB_STATUS_MAINTENANCE=5 ;//CAB_STATUS_DEACTIVATED=4
  String ROLE_CAB="cab",ROLE_VENDOR="vendor",ROLE_DRIVER="driver";

}
