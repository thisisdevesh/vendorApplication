
package com.gts.coordinator.Model.ContractorData.ContractorDetail;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//@Entity(tableName = "vendor_list")
public class VendorList {
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    @NonNull
//    int id;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("DriverList")
    @Expose
    public List<DriverList> driverList = null;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("is_cab_available")
    @Expose
    private Boolean isCabAvailable;
    @SerializedName("is_verified_vndor")
    @Expose
    private Boolean isVerifiedVndor;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("vendor_balance")
    @Expose
    private Double vendorBalance;
    @PrimaryKey
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("vendor_status")
    @Expose
    private Integer vendorStatus;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DriverList> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<DriverList> driverList) {
        this.driverList = driverList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsCabAvailable() {
        return isCabAvailable;
    }

    public void setIsCabAvailable(Boolean isCabAvailable) {
        this.isCabAvailable = isCabAvailable;
    }

    public Boolean getIsVerifiedVndor() {
        return isVerifiedVndor;
    }

    public void setIsVerifiedVndor(Boolean isVerifiedVndor) {
        this.isVerifiedVndor = isVerifiedVndor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getVendorBalance() {
        return vendorBalance;
    }

    public void setVendorBalance(Double vendorBalance) {
        this.vendorBalance = vendorBalance;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(Integer vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

}