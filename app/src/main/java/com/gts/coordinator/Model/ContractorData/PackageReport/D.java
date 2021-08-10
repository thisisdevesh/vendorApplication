package com.gts.coordinator.Model.ContractorData.PackageReport;

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
@SerializedName("service_list")
@Expose
private List<ServiceList> serviceList = null;

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

public List<ServiceList> getServiceList() {
return serviceList;
}

public void setServiceList(List<ServiceList> serviceList) {
this.serviceList = serviceList;
}

}