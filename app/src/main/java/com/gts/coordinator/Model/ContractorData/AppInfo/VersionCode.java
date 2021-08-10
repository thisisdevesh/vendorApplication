package com.gts.coordinator.Model.ContractorData.AppInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionCode {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("VersionName")
@Expose
private String versionName;
@SerializedName("VersionCode")
@Expose
private Integer versionCode;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public String getVersionName() {
return versionName;
}

public void setVersionName(String versionName) {
this.versionName = versionName;
}

public Integer getVersionCode() {
return versionCode;
}

public void setVersionCode(Integer versionCode) {
this.versionCode = versionCode;
}

}