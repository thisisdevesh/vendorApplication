
package com.gts.coordinator.Model.ContractorData.BookingActivities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetBookingActivitiesData {

    @SerializedName("contid")
    @Expose
    private String contid;

    public SetBookingActivitiesData(String cont_id) {
        this.contid = cont_id;
    }

    public String getContid() {
        return contid;
    }

    public void setContid(String contid) {
        this.contid = contid;
    }

}
