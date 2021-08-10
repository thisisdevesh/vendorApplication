package com.gts.coordinator.Model.ContractorData.LoginDetail;

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
@SerializedName("sessions")
@Expose
private List<Session> sessions = null;

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

public List<Session> getSessions() {
return sessions;
}

public void setSessions(List<Session> sessions) {
this.sessions = sessions;
}

}
