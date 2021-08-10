package com.gts.coordinator.dao;

/**
 * Created by GTS Developer on 28-May-2017 @ 16:52
 */

public class MaintenanceReportDao
{
  String inDate, inTime,outDate,outTime,totalMaintenaceTime;

  public MaintenanceReportDao(String inDate, String inTime, String outDate, String outTime, String totalMaintenanceTime) {
    this.inDate = inDate;
    this.inTime = inTime;
    this.outDate = outDate;
    this.outTime = outTime;
    this.totalMaintenaceTime = totalMaintenanceTime;
  }

  public String getInDate() {
    return inDate;
  }

  public String getInTime() {
    return inTime;
  }

  public String getOutDate() {
    return outDate;
  }

  public String getOutTime() {
    return outTime;
  }

  public String getTotalMaintenanceTime() {
    return totalMaintenaceTime;
  }
}
