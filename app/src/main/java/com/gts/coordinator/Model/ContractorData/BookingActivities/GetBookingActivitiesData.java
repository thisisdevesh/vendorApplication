
package com.gts.coordinator.Model.ContractorData.BookingActivities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingActivitiesData {

    @SerializedName("assignList")
    @Expose
    private List<BookingActivitiesList> bookingActivitiesList = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private Integer status;

    public List<BookingActivitiesList> getBookingActivitiesList() {
        return bookingActivitiesList;
    }

    public void setBookingActivitiesList(List<BookingActivitiesList> bookingActivitiesList) {
        this.bookingActivitiesList = bookingActivitiesList;
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
