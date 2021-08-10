package com.gts.coordinator.Model.en;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostMobileNo {

    @SerializedName("phone")
    @Expose
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
