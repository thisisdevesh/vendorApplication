
package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetusNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("Response")
    @Expose
    private Response response;
    @SerializedName("cabid")
    @Expose
    private String cabid;

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

    public String getCabid() {
        return cabid;
    }

    public void setCabid(String cabid) {
        this.cabid = cabid;
    }

}
