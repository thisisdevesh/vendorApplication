package com.gts.coordinator.Model.ContractorData.UnreadNotificationCount;

import com.google.gson.annotations.SerializedName;

public class PostData {
    @SerializedName("id")
    private int id;
    @SerializedName("appname")
    private String appname;
    public PostData(int id, String appname) {
        this.id = id;
        this.appname = appname;
    }
}
