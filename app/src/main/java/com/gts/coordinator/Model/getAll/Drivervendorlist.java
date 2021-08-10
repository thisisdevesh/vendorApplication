package com.gts.coordinator.Model.getAll;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "driver_vendor_list")
public class Drivervendorlist {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_data")
    @NonNull
    int id_data;
    @SerializedName("Maintenance")
    @Expose
    private Integer maintenance;
    @SerializedName("Deactivated")
    @Expose
    private String deactivated;
    @SerializedName("Driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("Driver_name")
    @Expose
    @Nullable
    private String driverName;
    @SerializedName("Driver_no")
    @Expose
    private String driverNo;
    @SerializedName("Driver_veryfy_status")
    @Expose
    private String driverVeryfyStatus;
    @SerializedName("Driver_lat_long")
    @Expose
    private String driverLatLong;
    @SerializedName("Cab_no")
    @Expose
    private String cabNo;
    @SerializedName("cab_type")
    @Expose
    private String cabType;
    @SerializedName("Vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("Vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("Vendor_no")
    @Expose
    private String vendorNo;
    @SerializedName("Vendor_verify_status")
    @Expose
    private Boolean vendorVerifyStatus;
    @SerializedName("cab_status")
    @Expose
    private String cabStatus;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("netbalance")
    @Expose
    private Double netbalance;

    public Drivervendorlist(Integer maintenance, String deactivated, Integer driverId, @Nullable String driverName, String driverNo, String driverVeryfyStatus, String driverLatLong, String cabNo, String cabType, Integer vendorId,
                            String vendorName, String vendorNo, Boolean vendorVerifyStatus, String cabStatus, Integer id, Double netbalance) {
        this.maintenance = maintenance;
        this.deactivated = deactivated;
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverNo = driverNo;
        this.driverVeryfyStatus = driverVeryfyStatus;
        this.driverLatLong = driverLatLong;
        this.cabNo = cabNo;
        this.cabType = cabType;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorNo = vendorNo;
        this.vendorVerifyStatus = vendorVerifyStatus;
        this.cabStatus = cabStatus;
        this.id = id;
        this.netbalance = netbalance;
    }

    public int getId_data() {
        return id_data;
    }

    public void setId_data(int id_data) {
        this.id_data = id_data;
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

    public String getDriverNo() {
        return driverNo;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo;
    }

    public String getDriverVeryfyStatus() {
        return driverVeryfyStatus;
    }

    public void setDriverVeryfyStatus(String driverVeryfyStatus) {
        this.driverVeryfyStatus = driverVeryfyStatus;
    }

    public String getDriverLatLong() {
        return driverLatLong;
    }

    public void setDriverLatLong(String driverLatLong) {
        this.driverLatLong = driverLatLong;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(String vendorNo) {
        this.vendorNo = vendorNo;
    }

    public Boolean getVendorVerifyStatus() {
        return vendorVerifyStatus;
    }

    public void setVendorVerifyStatus(Boolean vendorVerifyStatus) {
        this.vendorVerifyStatus = vendorVerifyStatus;
    }

    public String getCabStatus() {
        return cabStatus;
    }

    public void setCabStatus(String cabStatus) {
        this.cabStatus = cabStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNetbalance() {
        return netbalance;
    }

    public void setNetbalance(Double netbalance) {
        this.netbalance = netbalance;
    }

    public Integer getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Integer maintenance) {
        this.maintenance = maintenance;
    }

    public String getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(String deactivated) {
        this.deactivated = deactivated;
    }
    @Override
    public String toString() {
        return String.format("[ %s | %s | %s | %s ]", vendorId, driverId, vendorName, vendorNo) ;
    }
}