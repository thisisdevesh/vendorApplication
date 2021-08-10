package com.gts.coordinator.Model.ContractorData.MaintenanceReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class D {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Response")
@Expose
private Response response;
@SerializedName("records")
@Expose
private List<Record> records = null;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}

public List<Record> getRecords() {
return records;
}

public void setRecords(List<Record> records) {
this.records = records;
}

}