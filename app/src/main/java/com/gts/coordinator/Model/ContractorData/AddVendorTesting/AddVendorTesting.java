package com.gts.coordinator.Model.ContractorData.AddVendorTesting;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddVendorTesting {

    @SerializedName("Response")
    @Expose
    private Response response;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;

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
