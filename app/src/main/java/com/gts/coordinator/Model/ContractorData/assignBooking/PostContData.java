package com.gts.coordinator.Model.ContractorData.assignBooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostContData {
    @SerializedName("contid")
    @Expose
    private long cont_id;

    public PostContData(long cont_id) {
        this.cont_id = cont_id;
    }

    public long getCont_id() {
        return cont_id;
    }

    public void setCont_id(long cont_id) {
        this.cont_id = cont_id;
    }
}
