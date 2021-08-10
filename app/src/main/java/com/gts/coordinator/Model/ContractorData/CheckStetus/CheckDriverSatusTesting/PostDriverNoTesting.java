package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverSatusTesting;

import com.google.gson.annotations.SerializedName;

public class PostDriverNoTesting {
    @SerializedName("driverphone")
    String driverphone;

    public PostDriverNoTesting(String driverphone) {
        this.driverphone = driverphone;
    }
}
