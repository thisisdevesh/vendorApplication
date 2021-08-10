package com.gts.coordinator.Model.ContractorData.ContWalateData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class D {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("History")
@Expose
private List<History> history = null;
@SerializedName("Response")
@Expose
private Response response;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<History> getHistory() {
return history;
}

public void setHistory(List<History> history) {
this.history = history;
}

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}

}