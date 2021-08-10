package com.gts.coordinator.Model.ContractorData.assignBooking;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "assign_booking_list")
public class BookingList {
    @PrimaryKey
    @SerializedName("bookingId")
    @Expose
    @NonNull
    private String bookingId;
    @SerializedName("planType")
    @Expose
    private String planType;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("datetime")
    @Expose
    private String datetime;
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
    @SerializedName("cabCategory")
    @Expose
    private String cabCategory;
    @SerializedName("bookingDays")
    @Expose
    private Integer bookingDays;
    @SerializedName("terrifType")
    @Expose
    private String terrifType;

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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getCabCategory() {
        return cabCategory;
    }

    public void setCabCategory(String cabCategory) {
        this.cabCategory = cabCategory;
    }

    public Integer getBookingDays() {
        return bookingDays;
    }

    public void setBookingDays(Integer bookingDays) {
        this.bookingDays = bookingDays;
    }

    public String getTerrifType() {
        return terrifType;
    }

    public void setTerrifType(String terrifType) {
        this.terrifType = terrifType;
    }

}