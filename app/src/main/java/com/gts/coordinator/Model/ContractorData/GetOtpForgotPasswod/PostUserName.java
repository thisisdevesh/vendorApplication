package com.gts.coordinator.Model.ContractorData.GetOtpForgotPasswod;

import com.google.gson.annotations.SerializedName;

public class PostUserName  {
    @SerializedName("username")
    private String username;

    public PostUserName(String username) {
        this.username = username;
    }
}
