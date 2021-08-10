package com.gts.coordinator.Model.ContractorData.assignDriver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class DriverDatum {

    @SerializedName("packageType")
    @Expose
    private String packageType;
    @SerializedName("totalDays")
    @Expose
    private String totalDays;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("rideTime")
    @Expose
    private String rideTime;
    @SerializedName("totalAmount")
    @Expose
    private Double totalAmount;
    @SerializedName("advanceAmount")
    @Expose
    private Double advanceAmount;
    @SerializedName("fromLocation")
    @Expose
    private String fromLocation;
    @SerializedName("ToLocation")
    @Expose
    private String toLocation;

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

}