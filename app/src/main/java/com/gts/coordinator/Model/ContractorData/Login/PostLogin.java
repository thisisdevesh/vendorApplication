package com.gts.coordinator.Model.ContractorData.Login;

import com.google.gson.annotations.SerializedName;

public class PostLogin {
    @SerializedName("user_name")
    private String user_name;

    @SerializedName("password")
    private String password;

    @SerializedName("version_name")
    private String version_name;

    @SerializedName("version_code")
    private double version_code;

    @SerializedName("platform")
    private String platform;

    @SerializedName("fcm_id")
    private String fcm_id;

    @SerializedName("os_version_code")
    private double os_version_code;

    @SerializedName("os_version_name")
    private String os_version_name;

    @SerializedName("device_model")
    private String device_model;

    @SerializedName("device_id")
    private String device_id;

    public PostLogin(String user_name, String password, String version_name, double version_code, String platform, String fcm_id, double os_version_code, String os_version_name, String device_model, String device_id) {
        this.user_name = user_name;
        this.password = password;
        this.version_name = version_name;
        this.version_code = version_code;
        this.platform = platform;
        this.fcm_id = fcm_id;
        this.os_version_code = os_version_code;
        this.os_version_name = os_version_name;
        this.device_model = device_model;
        this.device_id = device_id;
    }

}
