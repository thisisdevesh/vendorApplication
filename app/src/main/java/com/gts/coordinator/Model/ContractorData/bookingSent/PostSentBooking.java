package com.gts.coordinator.Model.ContractorData.bookingSent;

import com.google.gson.annotations.SerializedName;

public class PostSentBooking {
    @SerializedName("bookingId")
   private String bookingId;
    @SerializedName("cabNumber")
   private String cabNumber;

    private int bookingReuqestType;

    public PostSentBooking(String bookingId, String cabNumber,int bookingReuqestType) {
        this.bookingId = bookingId;
        this.cabNumber = cabNumber;
        this.bookingReuqestType = bookingReuqestType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCabNumber() {
        return cabNumber;
    }

    public void setCabNumber(String cabNumber) {
        this.cabNumber = cabNumber;
    }

    public int getBookingReuqestType() {
        return bookingReuqestType;
    }

    public void setBookingReuqestType(int bookingReuqestType) {
        this.bookingReuqestType = bookingReuqestType;
    }
}
