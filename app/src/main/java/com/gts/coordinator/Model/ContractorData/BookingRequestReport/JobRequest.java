package com.gts.coordinator.Model.ContractorData.BookingRequestReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobRequest {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Accepted")
@Expose
private Integer accepted;
@SerializedName("Not_Considered")
@Expose
private Integer notConsidered;
@SerializedName("Not_Responded")
@Expose
private Integer notResponded;
@SerializedName("Rejected")
@Expose
private Integer rejected;
@SerializedName("date")
@Expose
private String date;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Integer getAccepted() {
return accepted;
}

public void setAccepted(Integer accepted) {
this.accepted = accepted;
}

public Integer getNotConsidered() {
return notConsidered;
}

public void setNotConsidered(Integer notConsidered) {
this.notConsidered = notConsidered;
}

public Integer getNotResponded() {
return notResponded;
}

public void setNotResponded(Integer notResponded) {
this.notResponded = notResponded;
}

public Integer getRejected() {
return rejected;
}

public void setRejected(Integer rejected) {
this.rejected = rejected;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

}