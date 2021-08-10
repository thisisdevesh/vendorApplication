package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetusNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDriverNoNew {
    @SerializedName("driverphone")
    @Expose
    private String driverphone;

    public PostDriverNoNew(String driverPhno) {
        this.driverphone=driverPhno;
    }

    public String getDriverphone() {
        return driverphone;
    }

    public void setDriverphone(String driverphone) {
        this.driverphone = driverphone;
    }
}
