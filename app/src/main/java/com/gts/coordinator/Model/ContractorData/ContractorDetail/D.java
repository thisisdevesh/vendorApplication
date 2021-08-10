
package com.gts.coordinator.Model.ContractorData.ContractorDetail;

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
    @SerializedName("VendorList")
    @Expose
    private List<VendorList> vendorList = null;

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

    public List<VendorList> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<VendorList> vendorList) {
        this.vendorList = vendorList;
    }

}