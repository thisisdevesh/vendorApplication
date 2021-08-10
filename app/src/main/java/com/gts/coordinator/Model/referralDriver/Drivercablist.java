package com.gts.coordinator.Model.referralDriver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drivercablist {

    @SerializedName("cabNumber")
    @Expose
    private String cabNumber;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("totalcount")
    @Expose
    private String totalcount;

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

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

}