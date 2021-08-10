package com.gts.coordinator.Model.ContractorData.activeDriver;

public class PostActiveDriver {
   private long contId;
   private String bookingId;
   private String cabNumber;
   private int bookingReuqestType;

    public PostActiveDriver(long contId, String bookingId, String cabNumber, int bookingReuqestType) {
        this.contId = contId;
        this.bookingId = bookingId;
        this.cabNumber = cabNumber;
        this.bookingReuqestType = bookingReuqestType;
    }

    public long getContId() {
        return contId;
    }

    public void setContId(long contId) {
        this.contId = contId;
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
    /*  public PostActiveDriver(long contId, String bookingId) {
        this.contId = contId;
        this.bookingId = bookingId;
    }

    public long getContId() {
        return contId;
    }

    public void setContId(long contId) {
        this.contId = contId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }*/
}
