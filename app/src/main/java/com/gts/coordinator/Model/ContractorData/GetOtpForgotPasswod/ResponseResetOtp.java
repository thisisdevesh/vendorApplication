package com.gts.coordinator.Model.ContractorData.GetOtpForgotPasswod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseResetOtp {

    @SerializedName("getresponse")
    @Expose
    private Getresponse getresponse;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;

    public Getresponse getGetresponse() {
        return getresponse;
    }

    public void setGetresponse(Getresponse getresponse) {
        this.getresponse = getresponse;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }



}