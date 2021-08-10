package com.gts.coordinator.Model.ContractorData.WallteHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryDetailsResponce {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

}


