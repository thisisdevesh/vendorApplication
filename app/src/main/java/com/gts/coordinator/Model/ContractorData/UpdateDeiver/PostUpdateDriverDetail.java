package com.gts.coordinator.Model.ContractorData.UpdateDeiver;

import com.google.gson.annotations.SerializedName;

public class PostUpdateDriverDetail {
    @SerializedName("vcab_id")
    long  vcab_id;
    @SerializedName("driver_phone")
    String  driver_phone;

    public PostUpdateDriverDetail(long vcab_id, String driver_phone) {
        this.vcab_id = vcab_id;
        this.driver_phone = driver_phone;
    }
}
