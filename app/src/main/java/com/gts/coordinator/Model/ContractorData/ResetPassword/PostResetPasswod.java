package com.gts.coordinator.Model.ContractorData.ResetPassword;

import com.google.gson.annotations.SerializedName;

public class PostResetPasswod {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public PostResetPasswod(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
