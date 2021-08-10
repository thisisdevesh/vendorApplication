package com.gts.coordinator.Model.ContractorData.GetOtp;

import com.google.gson.annotations.SerializedName;

public class PostMobileNo {
    @SerializedName("phone")
    String phone;

    public PostMobileNo(String phone) {
        this.phone = phone;
    }
}
