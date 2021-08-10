package com.gts.coordinator.Model.ContractorData.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginModel {

    @SerializedName("d")
    @Expose
    private DataLogin d;

    public DataLogin getD() {
        return d;
    }

    public void setD(DataLogin d) {
        this.d = d;
    }

}