package com.gts.coordinator.Model.ContractorData.LoginDetail;

import com.google.gson.annotations.SerializedName;

public class PostDetail {

    @SerializedName("vid")
    long vid;
    @SerializedName("vcabid")
    long vcabid;

    public PostDetail(long vid, long vcabid) {
        this.vid = vid;
        this.vcabid = vcabid;
    }

}
