package com.gts.coordinator.dao;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by GTS Developer on 18-May-2017 @ 11:02
 */

public class SessionDao {
  String loginLoutStatus;
//  String datetime;
//  Date date;
Calendar time;
  public SessionDao() {
  }

  public SessionDao(String loginLoutStatus, Calendar time) {
    this.loginLoutStatus = loginLoutStatus;
    this.time = time;
//    this.date = date;
  }

  public String getLoginLoutStatus() {
    return loginLoutStatus;
  }

  public Calendar getTime() {
    return time;
  }

  /*public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
*/
  public void setLoginLoutStatus(String loginLoutStatus) {
    this.loginLoutStatus = loginLoutStatus;
  }

  public void setTime(Calendar time) {
    this.time = time;
  }
}
