package com.gts.coordinator.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GTS Developer on 18-Jan-2017 @ 17:56
 */

public class Driver implements Parcelable {
  public static final byte DRIVER_STATUS_FREE = 1, DRIVER_STATUS_BUSY = 2, DRIVER_STATUS_LOGGED_OUT = 3, DRIVER_STATUS_BLACK_LISTED = 4;
  private String name, phoneNo,cabNo,category,cabName;
//  currentLocation
  private double lat, lng;
  private byte status;
  private boolean underMaintenence, isVerified;
  private long driverId,vendorId,cabCatID;

  public Driver() {
  }

  public Driver(long driverId, double lat, double lng, String name, String phoneNo, byte status, boolean underMaintenence,
                long vendorId,String cabNo,String category,String cabName,long cabCatId,boolean isVerified) {
    this.driverId = driverId;
    this.lat = lat;
    this.lng = lng;
    this.name = name;
    this.phoneNo = phoneNo;
    this.status = status;
    this.underMaintenence = underMaintenence;
    this.vendorId = vendorId;
    this.cabNo = cabNo;
    this.category = category;
    this.cabName = cabName;
    this.cabCatID = cabCatId;
 this.isVerified = isVerified;
//    this.currentLocation = currentLocation;
  }




  public Driver(Parcel in) {
    phoneNo = in.readString();
    name = in.readString();
    lat = in.readDouble();
    lng = in.readDouble();
    status = in.readByte();
    underMaintenence = in.readByte() == 1;
    driverId = in.readLong();
    vendorId = in.readLong();
    cabNo = in.readString();
    category = in.readString();
    cabName = in.readString();
    cabCatID = in.readLong();
//    currentLocation = in.readString();

  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public String getCabNo() {
    return cabNo;
  }

  public long getVendorId() {
    return vendorId;
  }

  public byte getStatus() {
    return status;
  }

  public String getCategory() {
    return category;
  }

  public String getCabName() {
    return cabName;
  }

  public long getDriverId() {
    return driverId;
  }

  public boolean
  isVerified() {
    return isVerified;
  }

/*
  public String getCurrentLocation() {
    return currentLocation;
  }
*/

  public static byte getDriverStatusBlackListed() {
    return DRIVER_STATUS_BLACK_LISTED;
  }

  public static byte getDriverStatusBusy() {
    return DRIVER_STATUS_BUSY;
  }

  public static byte getDriverStatusFree() {
    return DRIVER_STATUS_FREE;
  }

  public static byte getDriverStatusLoggedOut() {
    return DRIVER_STATUS_LOGGED_OUT;
  }

  public static final Creator<Driver> CREATOR = new Creator<Driver>() {
    @Override
    public Driver createFromParcel(Parcel source) {
      return new Driver(source);
    }

    @Override
    public Driver[] newArray(int size) {
      return new Driver[size];
    }
  };


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(phoneNo);
    dest.writeString(name);
    dest.writeDouble(lat);
    dest.writeDouble(lng);
    dest.writeByte(status);
    dest.writeByte((byte) (underMaintenence ? 1 : 0));
    dest.writeLong(driverId);
    dest.writeLong(vendorId);
    dest.writeString(cabNo);
    dest.writeString(category);
    dest.writeString(cabName);
    dest.writeLong(cabCatID);
//    dest.writeString(currentLocation);
  }

    @Override
    public String toString() {
        return String.format("[ %s | %s | %s | %s ]", vendorId, driverId, name, phoneNo) ;
    }
}
