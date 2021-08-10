package com.gts.coordinator.Model.ContractorData.ContWalateData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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