package com.gts.coordinator.Model.referralDriver;

import com.google.gson.annotations.SerializedName;

public class PostNo {
    @SerializedName("driverPhone")
    String driverPhone;

    public PostNo(String driverPhone) {
        this.driverPhone = driverPhone;
    }
}
