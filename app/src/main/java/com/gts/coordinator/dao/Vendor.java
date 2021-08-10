package com.gts.coordinator.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GTS Developer on 28-Jan-2017 @ 13:00
 */

public class Vendor  implements Parcelable {
  String name, phno , address;
  long vndId,venBal;
  boolean isVerified;
  //TODO :  id will be use to get drivers under particular vendor
  public static final byte VENDOR_STATUS_WORKING = 1 ,VENDOR_STATUS_BLACK_LISTED=2 ;
  private byte status ;
//  double lat,lng;

//  public Vendor(String address, String email, double lat, double lng, String name, String phno,byte status)

  public  Vendor()
  {}

  public Vendor(String name, String phno, String address, long vndId, long venBal, boolean isVerified, byte status) {
    this.name = name;
    this.phno = phno;
    this.address = address;
    this.vndId = vndId;
    this.venBal = venBal;
    this.isVerified = isVerified;
    this.status = status;
  }
  /*

  public Vendor(String address, String name, String phno, byte status, long vndId ,boolean isVerified) {
    this.address = address;
    this.name = name;
    this.phno = phno;
    this.status = status;
    this.vndId = vndId;
    this.isVerified = isVerified;
  }
*/


  public Vendor(Parcel in){
    address = in.readString();
    name = in.readString();
    phno = in.readString();
    status = in.readByte();
    vndId = in.readLong();
    venBal = in.readLong();
//    isVerified = in.readb

//    lat = in.readDouble();
//    lng = in.readDouble();

  }


  public String getName() {
    return name;
  }

  public String getPhno() {
    return phno;
  }

  public long getVndId() {
    return vndId;
  }

  public byte getStatus() {
    return status;
  }

  public boolean isVerified() {
    return isVerified;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(phno);
    dest.writeString(address);
    dest.writeByte(status);
    dest.writeLong(vndId);
    dest.writeLong(venBal);
//    dest.writeDouble(lat);
//    dest.writeDouble(lng);
  }

  public static final Creator<Vendor> CREATOR = new Creator<Vendor>(){
    @Override
    public Vendor createFromParcel(Parcel source) {
      return new Vendor(source);
    }

    @Override
    public Vendor[] newArray(int size) {
      return new Vendor[size];
    }
  };

/*
  public double getLat() {
    return lat;
  }
*/

/*
  public void setLat(double lat) {
    this.lat = lat;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }
*/

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoneNo(String phno) {
    this.phno = phno;
  }

/*
  public double getLng() {
    return lng;
  }
*/

  public long getVenBal() {
    return venBal;
  }

  public String getAddress() {
    return address;
  }

  public static Creator<Vendor> getCREATOR() {
    return CREATOR;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return String.format("%s | %s | %s | %s | %s", vndId, name, status, phno, venBal);
  }
}
