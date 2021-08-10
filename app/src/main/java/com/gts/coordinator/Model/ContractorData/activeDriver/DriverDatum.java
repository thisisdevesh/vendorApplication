package com.gts.coordinator.Model.ContractorData.activeDriver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
@Entity(tableName = "active_driver_list")
*/
public class DriverDatum {

    @SerializedName("vendorPhone")
    @Expose
    private String vendorPhone;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("cabNumber")
    @Expose
    private String cabNumber;
    @SerializedName("assignedBooking")
    @Expose
    private String assignedBooking;
    @SerializedName("isFree")
    @Expose
    private Boolean isFree;

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public String getCabNumber() {
        return cabNumber;
    }

    public void setCabNumber(String cabNumber) {
        this.cabNumber = cabNumber;
    }

    public String getAssignedBooking() {
        return assignedBooking;
    }

    public void setAssignedBooking(String assignedBooking) {
        this.assignedBooking = assignedBooking;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

}