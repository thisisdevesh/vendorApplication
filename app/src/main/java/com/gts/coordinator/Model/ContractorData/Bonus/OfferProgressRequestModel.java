package com.gts.coordinator.Model.ContractorData.Bonus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferProgressRequestModel {

    @SerializedName("contId")
    @Expose
    private String contId;

    public OfferProgressRequestModel(String contractorID) {
        contId = contractorID;
    }

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

}