package com.gts.coordinator.Model.ContractorData.assignBooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingDataResponse {

@SerializedName("bookingList")
@Expose
private List<BookingList> bookingList = null;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Status")
@Expose
private Integer status;

public List<BookingList> getBookingList() {
return bookingList;
}

public void setBookingList(List<BookingList> bookingList) {
this.bookingList = bookingList;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

}