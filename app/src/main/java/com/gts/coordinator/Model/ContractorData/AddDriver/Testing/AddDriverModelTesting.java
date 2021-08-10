package com.gts.coordinator.Model.ContractorData.AddDriver.Testing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDriverModelTesting {

    @SerializedName("getresponse")
    @Expose
    private Getresponse getresponse;
    @SerializedName("otp")
    @Expose
    private String otp;

    public Getresponse getGetresponse() {
        return getresponse;
    }

    public void setGetresponse(Getresponse getresponse) {
        this.getresponse = getresponse;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}