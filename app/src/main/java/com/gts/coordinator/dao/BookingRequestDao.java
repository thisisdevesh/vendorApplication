package com.gts.coordinator.dao;

import java.util.Calendar;

/**
 * Created by GTS Developer on 20-May-2017 @ 12:37
 */

public class BookingRequestDao {
  String accepted ,notConsidered,notResponded,redjected,date;

  public BookingRequestDao(String accepted, String notConsidered, String notResponded, String redjected, String date)
  {
    this.accepted = accepted;
    this.notConsidered = notConsidered;
    this.notResponded = notResponded;
    this.redjected = redjected;
    this.date = date;
  }



  public String getAccepted() {
    return accepted;
  }

  public String getNotConsidered() {
    return notConsidered;
  }

  public String getNotResponded() {
    return notResponded;
  }

  public String getRedjected() {
    return redjected;
  }

  public String getDate() {
    return date;
  }
}

