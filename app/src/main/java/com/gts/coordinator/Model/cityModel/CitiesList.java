
package com.gts.coordinator.Model.cityModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitiesList implements Parcelable
{

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("CityID")
    @Expose
    private String cityID;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("IsActive")
    @Expose
    private String isActive;
    @SerializedName("google_area_code")
    @Expose
    private String googleAreaCode;
    @SerializedName("lat_lng")
    @Expose
    private Object latLng;
    public final static Parcelable.Creator<CitiesList> CREATOR = new Creator<CitiesList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CitiesList createFromParcel(Parcel in) {
            return new CitiesList(in);
        }

        public CitiesList[] newArray(int size) {
            return (new CitiesList[size]);
        }

    }
            ;

    protected CitiesList(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.cityID = ((String) in.readValue((String.class.getClassLoader())));
        this.cityName = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((String) in.readValue((String.class.getClassLoader())));
        this.googleAreaCode = ((String) in.readValue((String.class.getClassLoader())));
        this.latLng = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public CitiesList() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getGoogleAreaCode() {
        return googleAreaCode;
    }

    public void setGoogleAreaCode(String googleAreaCode) {
        this.googleAreaCode = googleAreaCode;
    }

    public Object getLatLng() {
        return latLng;
    }

    public void setLatLng(Object latLng) {
        this.latLng = latLng;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(cityID);
        dest.writeValue(cityName);
        dest.writeValue(isActive);
        dest.writeValue(googleAreaCode);
        dest.writeValue(latLng);
    }

    public int describeContents() {
        return 0;
    }

}