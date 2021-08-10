
package com.gts.coordinator.Model.ContractorData.ContractorDetail;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "driver_list")
public class DriverList {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @ColumnInfo(name = "type")
    @SerializedName("__type")
    @Expose
    private String type;
    @ColumnInfo(name = "cabCategoryCode")
    @SerializedName("cab_category_code")
    @Expose
    private String cabCategoryCode;
    @ColumnInfo(name = "cabCategoryId")
    @SerializedName("cab_category_id")
    @Expose
    private Integer cabCategoryId;

    @ColumnInfo(name = "cabNumber")
    @SerializedName("cab_number")
    @Expose
    private String cabNumber;
    @ColumnInfo(name = "cabStatus")
    @SerializedName("cab_status")
    @Expose
    private Integer cabStatus;
    @ColumnInfo(name = "cabType")
    @SerializedName("cab_type")
    @Expose
    private Integer cabType;
    @ColumnInfo(name = "cabTypeId")
    @SerializedName("cab_type_id")
    @Expose
    private Integer cabTypeId;
    @ColumnInfo(name = "carName")
    @SerializedName("car_name")
    @Expose
    private String carName;
    @ColumnInfo(name = "categoryName")
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @ColumnInfo(name = "currentLocation")
    @SerializedName("current_location")
    @Expose
    private String currentLocation;
    @ColumnInfo(name = "driverId")
    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @ColumnInfo(name = "driverName")
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @ColumnInfo(name = "driverPhone")
    @SerializedName("driver_phone")
    @Expose
    private String driverPhone;
    @ColumnInfo(name = "isFree")
    @SerializedName("is_free")
    @Expose
    private Boolean isFree;
    @ColumnInfo(name = "isVerifiedCab")
    @SerializedName("is_verified_cab")
    @Expose
    private Boolean isVerifiedCab;
    @ColumnInfo(name = "maintenance")
    @SerializedName("maintenance")
    @Expose
    private Boolean maintenance;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCabCategoryCode() {
        return cabCategoryCode;
    }

    public void setCabCategoryCode(String cabCategoryCode) {
        this.cabCategoryCode = cabCategoryCode;
    }

    public Integer getCabCategoryId() {
        return cabCategoryId;
    }

    public void setCabCategoryId(Integer cabCategoryId) {
        this.cabCategoryId = cabCategoryId;
    }

    public String getCabNumber() {
        return cabNumber;
    }

    public void setCabNumber(String cabNumber) {
        this.cabNumber = cabNumber;
    }

    public Integer getCabStatus() {
        return cabStatus;
    }

    public void setCabStatus(Integer cabStatus) {
        this.cabStatus = cabStatus;
    }

    public Integer getCabType() {
        return cabType;
    }

    public void setCabType(Integer cabType) {
        this.cabType = cabType;
    }

    public Integer getCabTypeId() {
        return cabTypeId;
    }

    public void setCabTypeId(Integer cabTypeId) {
        this.cabTypeId = cabTypeId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsVerifiedCab() {
        return isVerifiedCab;
    }

    public void setIsVerifiedCab(Boolean isVerifiedCab) {
        this.isVerifiedCab = isVerifiedCab;
    }

    public Boolean getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }

}