package com.gts.coordinator.Model.ContractorData.MaintenanceReport;

import com.google.gson.annotations.SerializedName;

public class PostVcabId {
    @SerializedName("vcabid")
    private long vcabid;

    public PostVcabId(long vcabid) {
        this.vcabid = vcabid;
    }
}
