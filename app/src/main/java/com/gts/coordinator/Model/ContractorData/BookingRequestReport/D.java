package com.gts.coordinator.Model.ContractorData.BookingRequestReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class D {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("JobRequests")
@Expose
private List<JobRequest> jobRequests = null;
@SerializedName("Response")
@Expose
private Response response;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<JobRequest> getJobRequests() {
return jobRequests;
}

public void setJobRequests(List<JobRequest> jobRequests) {
this.jobRequests = jobRequests;
}

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}

}