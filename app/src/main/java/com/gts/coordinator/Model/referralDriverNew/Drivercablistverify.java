package com.gts.coordinator.Model.referralDriverNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drivercablistverify {

@SerializedName("cabNumber")
@Expose
private String cabNumber;
@SerializedName("driverName")
@Expose
private String driverName;
@SerializedName("driverPhone")
@Expose
private String driverPhone;
@SerializedName("verifiedstatus")
@Expose
private String verifiedstatus;

public String getCabNumber() {
return cabNumber;
}

public void setCabNumber(String cabNumber) {
this.cabNumber = cabNumber;
}

public String getDriverName() {
return driverName;
}

public void setDriverName(String driverName) {
this.driverName = driverName;
}

public String getDriverPhone() {
return driverPhone;
}

public void setDriverPhone(String driverPhone) {
this.driverPhone = driverPhone;
}

public String getVerifiedstatus() {
return verifiedstatus;
}

public void setVerifiedstatus(String verifiedstatus) {
this.verifiedstatus = verifiedstatus;
}

}