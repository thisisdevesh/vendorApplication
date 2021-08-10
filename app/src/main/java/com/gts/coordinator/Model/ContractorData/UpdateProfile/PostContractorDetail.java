package com.gts.coordinator.Model.ContractorData.UpdateProfile;

import com.google.gson.annotations.SerializedName;

public class PostContractorDetail {
    @SerializedName("cont_id")
    long contId;
    @SerializedName("password")
    String password;
    @SerializedName("phone_number")
    String phone_number;
    @SerializedName("address")
    String address;

    public PostContractorDetail(long contId, String password, String phone_number, String address) {
        this.contId = contId;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
    }
}
