
package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckCabStetus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("getResponse")
    @Expose
    private GetResponse getResponse;
    @SerializedName("vcabid")
    @Expose
    private Integer vcabid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GetResponse getGetResponse() {
        return getResponse;
    }

    public void setGetResponse(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    public Integer getVcabid() {
        return vcabid;
    }

    public void setVcabid(Integer vcabid) {
        this.vcabid = vcabid;
    }

}
