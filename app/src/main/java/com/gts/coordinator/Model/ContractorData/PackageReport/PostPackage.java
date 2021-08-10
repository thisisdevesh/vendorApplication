package com.gts.coordinator.Model.ContractorData.PackageReport;

import com.google.gson.annotations.SerializedName;

public class PostPackage {
    @SerializedName("vcabid")
    private long vcabid;
    public PostPackage(long vcabid) {
        this.vcabid = vcabid;
    }
}
