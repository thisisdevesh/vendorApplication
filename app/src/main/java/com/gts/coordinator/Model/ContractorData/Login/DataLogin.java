package com.gts.coordinator.Model.ContractorData.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLogin {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Details")
@Expose
private Details details;
@SerializedName("Response")
@Expose
private Response response;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Details getDetails() {
return details;
}

public void setDetails(Details details) {
this.details = details;
}

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}

}
