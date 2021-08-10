package com.gts.coordinator.Model.ContractorData.AddDriver.Testing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDriverDetail {

    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("contId")
    @Expose
    private String contId;
    @SerializedName("cabNumber")
    @Expose
    private String cabNumber;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("driverDlNumber")
    @Expose
    private String driverDlNumber;
    @SerializedName("driverDlExpiryDate")
    @Expose
    private String driverDlExpiryDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("otpVerified")
    @Expose
    private String otpVerified;

    public PostDriverDetail(String vendorId, String contId, String cabNumber, String driverName, String driverPhone, String driverDlNumber, String driverDlExpiryDate, String status, String otpVerified) {
        this.vendorId = vendorId;
        this.contId = contId;
        this.cabNumber = cabNumber;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.driverDlNumber = driverDlNumber;
        this.driverDlExpiryDate = driverDlExpiryDate;
        this.status = status;
        this.otpVerified = otpVerified;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

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

    public String getDriverDlNumber() {
        return driverDlNumber;
    }

    public void setDriverDlNumber(String driverDlNumber) {
        this.driverDlNumber = driverDlNumber;
    }

    public String getDriverDlExpiryDate() {
        return driverDlExpiryDate;
    }

    public void setDriverDlExpiryDate(String driverDlExpiryDate) {
        this.driverDlExpiryDate = driverDlExpiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(String otpVerified) {
        this.otpVerified = otpVerified;
    }
}

