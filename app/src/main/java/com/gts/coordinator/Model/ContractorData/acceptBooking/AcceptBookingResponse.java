package com.gts.coordinator.Model.ContractorData.acceptBooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptBookingResponse {

@SerializedName("assignList")
@Expose
private List<AssignList> assignList = null;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Status")
@Expose
private Integer status;

public List<AssignList> getAssignList() {
return assignList;
}

public void setAssignList(List<AssignList> assignList) {
this.assignList = assignList;
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