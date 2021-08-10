package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetus;

import com.google.gson.annotations.SerializedName;

public class PostDriverNo {
    @SerializedName("driverphone")
    String driverphone;

    public PostDriverNo(String driverphone) {
        this.driverphone = driverphone;
    }
}
