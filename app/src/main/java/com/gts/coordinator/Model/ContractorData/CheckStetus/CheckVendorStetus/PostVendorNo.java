package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckVendorStetus;

import com.google.gson.annotations.SerializedName;

public class PostVendorNo {
    @SerializedName("phone")
    private  String phone;

    public PostVendorNo(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
