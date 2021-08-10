package com.gts.coordinator.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class D {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Response")
@Expose
private Response response;
@SerializedName("vcabid")
@Expose
private Integer vcabid;

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

public Integer getVcabid() {
return vcabid;
}

public void setVcabid(Integer vcabid) {
this.vcabid = vcabid;
}

}
