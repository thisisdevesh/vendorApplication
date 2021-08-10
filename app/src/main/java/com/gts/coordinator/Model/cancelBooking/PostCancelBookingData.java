package com.gts.coordinator.Model.cancelBooking;

public class PostCancelBookingData {
    private String bookingId;
    private String cabNumber;

    public PostCancelBookingData(String bookingId, String cabNumber) {
        this.bookingId = bookingId;
        this.cabNumber = cabNumber;
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
}
