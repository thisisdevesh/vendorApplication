package com.gts.coordinator.Model.bookingStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusRespose {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("response")
@Expose
private String response;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public String getResponse() {
return response;
}

public void setResponse(String response) {
this.response = response;
}

}