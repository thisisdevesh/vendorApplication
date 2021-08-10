package com.gts.coordinator.Model.ContractorData.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Current_Location")
@Expose
private Object currentLocation;
@SerializedName("DeviceID")
@Expose
private String deviceID;
@SerializedName("Device_Modal")
@Expose
private String deviceModal;
@SerializedName("GCM_ID")
@Expose
private String gCMID;
@SerializedName("ID")
@Expose
private Integer iD;
@SerializedName("Is_LoggedIn")
@Expose
private Object isLoggedIn;
@SerializedName("Os_Version_Code")
@Expose
private Integer osVersionCode;
@SerializedName("Os_Version_Name")
@Expose
private String osVersionName;
@SerializedName("Platform")
@Expose
private Object platform;
@SerializedName("User_Name")
@Expose
private String userName;
@SerializedName("Version_Code")
@Expose
private Integer versionCode;
@SerializedName("Version_Name")
@Expose
private String versionName;
@SerializedName("city_id")
@Expose
private Integer cityId;
@SerializedName("cont_address1")
@Expose
private String contAddress1;
@SerializedName("cont_address2")
@Expose
private String contAddress2;
@SerializedName("cont_name")
@Expose
private String contName;
@SerializedName("cont_phone1")
@Expose
private String contPhone1;
@SerializedName("cont_phone2")
@Expose
private String contPhone2;
@SerializedName("is_active")
@Expose
private Boolean isActive;
@SerializedName("is_synced")
@Expose
private Integer isSynced;
@SerializedName("password")
@Expose
private String password;
@SerializedName("profile_url")
@Expose
private String profileUrl;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Object getCurrentLocation() {
return currentLocation;
}

public void setCurrentLocation(Object currentLocation) {
this.currentLocation = currentLocation;
}

public String getDeviceID() {
return deviceID;
}

public void setDeviceID(String deviceID) {
this.deviceID = deviceID;
}

public String getDeviceModal() {
return deviceModal;
}

public void setDeviceModal(String deviceModal) {
this.deviceModal = deviceModal;
}

public String getGCMID() {
return gCMID;
}

public void setGCMID(String gCMID) {
this.gCMID = gCMID;
}

public Integer getID() {
return iD;
}

public void setID(Integer iD) {
this.iD = iD;
}

public Object getIsLoggedIn() {
return isLoggedIn;
}

public void setIsLoggedIn(Object isLoggedIn) {
this.isLoggedIn = isLoggedIn;
}

public Integer getOsVersionCode() {
return osVersionCode;
}

public void setOsVersionCode(Integer osVersionCode) {
this.osVersionCode = osVersionCode;
}

public String getOsVersionName() {
return osVersionName;
}

public void setOsVersionName(String osVersionName) {
this.osVersionName = osVersionName;
}

public Object getPlatform() {
return platform;
}

public void setPlatform(Object platform) {
this.platform = platform;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public Integer getVersionCode() {
return versionCode;
}

public void setVersionCode(Integer versionCode) {
this.versionCode = versionCode;
}

public String getVersionName() {
return versionName;
}

public void setVersionName(String versionName) {
this.versionName = versionName;
}

public Integer getCityId() {
return cityId;
}

public void setCityId(Integer cityId) {
this.cityId = cityId;
}

public String getContAddress1() {
return contAddress1;
}

public void setContAddress1(String contAddress1) {
this.contAddress1 = contAddress1;
}

public String getContAddress2() {
return contAddress2;
}

public void setContAddress2(String contAddress2) {
this.contAddress2 = contAddress2;
}

public String getContName() {
return contName;
}

public void setContName(String contName) {
this.contName = contName;
}

public String getContPhone1() {
return contPhone1;
}

public void setContPhone1(String contPhone1) {
this.contPhone1 = contPhone1;
}

public String getContPhone2() {
return contPhone2;
}

public void setContPhone2(String contPhone2) {
this.contPhone2 = contPhone2;
}

public Boolean getIsActive() {
return isActive;
}

public void setIsActive(Boolean isActive) {
this.isActive = isActive;
}

public Integer getIsSynced() {
return isSynced;
}

public void setIsSynced(Integer isSynced) {
this.isSynced = isSynced;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getProfileUrl() {
return profileUrl;
}

public void setProfileUrl(String profileUrl) {
this.profileUrl = profileUrl;
}

}