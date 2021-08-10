package com.gts.coordinator.Model.driverIncome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driverdatum {

@SerializedName("driverName")
@Expose
private String driverName;
@SerializedName("cabNo")
@Expose
private String cabNo;
@SerializedName("totalAmt")
@Expose
private Double totalAmt;
@SerializedName("driverPhone")
@Expose
private String driverPhone;
@SerializedName("totalCount")
@Expose
private String totalCount;

public String getDriverName() {
return driverName;
}

public void setDriverName(String driverName) {
this.driverName = driverName;
}

public String getCabNo() {
return cabNo;
}

public void setCabNo(String cabNo) {
this.cabNo = cabNo;
}

public Double getTotalAmt() {
return totalAmt;
}

public void setTotalAmt(Double totalAmt) {
this.totalAmt = totalAmt;
}

public String getDriverPhone() {
return driverPhone;
}

public void setDriverPhone(String driverPhone) {
this.driverPhone = driverPhone;
}

public String getTotalCount() {
return totalCount;
}

public void setTotalCount(String totalCount) {
this.totalCount = totalCount;
}

}