
package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckVendorStetus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("Response")
    @Expose
    private Response response;
    @SerializedName("vendorId")
    @Expose
    private Integer vendorId;

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

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

}
