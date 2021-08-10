package com.gts.coordinator.dao;

/**
 * Created by GTS Developer on 25-Mar-2017 @ 15:11
 */

public class CityList {
  String cityName;
  long  cityid;

  public CityList(long cityid, String cityName)
  {
    this.cityid = cityid;
    this.cityName = cityName;
  }

  public long getCityid() {
    return cityid;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityid(long cityid) {
    this.cityid = cityid;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }
}
