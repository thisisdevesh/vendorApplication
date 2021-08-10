package com.gts.coordinator.Model.ContractorData.WallteHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gts.coordinator.Model.ContractorData.WallteHistory.HistoryDetails;

public class History {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("history_details")
    @Expose
    private HistoryDetails historyDetails;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HistoryDetails getHistoryDetails() {
        return historyDetails;
    }

    public void setHistoryDetails(HistoryDetails historyDetails) {
        this.historyDetails = historyDetails;
    }

}