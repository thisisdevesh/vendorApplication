
package com.gts.coordinator.Model.ContractorData.BookingActivities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "booking_activities_list")
public class BookingActivitiesList {
    @PrimaryKey
    @NonNull
    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("planType")
    @Expose
    private String planType;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("fromDatetime")
    @Expose
    private String fromDatetime;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("paidWalletAmount")
    @Expose
    private Double paidWalletAmount;
    @SerializedName("fromLocation")
    @Expose
    private String fromLocation;
    @SerializedName("toLocation")
    @Expose
    private String toLocation;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorPhone")
    @Expose
    private String vendorPhone;
    @SerializedName("cabNumber")
    @Expose
    private String cabNumber;
    @SerializedName("bookingDays")
    @Expose
    private String bookingDays;
    @SerializedName("terrifType")
    @Expose
    private String terrifType;
    @SerializedName("cabType")
    @Expose
    private String cabType;
    @SerializedName("cabStatus")
    @Expose
    private String cabStatus;
    @SerializedName("deliveryStatus")
    @Expose
    private String deliveryStatus;
    @SerializedName("deliveryTime")
    @Expose
    private String deliveryTime;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }



    public String getFromDatetime() {
        return fromDatetime;
    }

    public void setFromDatetime(String fromDatetime) {
        this.fromDatetime = fromDatetime;
    }



    public Double getPaidWalletAmount() {
        return paidWalletAmount;
    }

    public void setPaidWalletAmount(Double paidWalletAmount) {
        this.paidWalletAmount = paidWalletAmount;
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public String getCabNumber() {
        return cabNumber;
    }

    public void setCabNumber(String cabNumber) {
        this.cabNumber = cabNumber;
    }


    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingDays() {
        return bookingDays;
    }

    public void setBookingDays(String bookingDays) {
        this.bookingDays = bookingDays;
    }

    public String getTerrifType() {
        return terrifType;
    }

    public void setTerrifType(String terrifType) {
        this.terrifType = terrifType;
    }

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    public String getCabStatus() {
        return cabStatus;
    }

    public void setCabStatus(String cabStatus) {
        this.cabStatus = cabStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

}