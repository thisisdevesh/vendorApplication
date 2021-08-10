
package com.gts.coordinator.Model.cityModel;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D implements Parcelable
{

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("CitiesList")
    @Expose
    private List<CitiesList> citiesList = null;
    @SerializedName("FromCities")
    @Expose
    private Object fromCities;
    @SerializedName("Response")
    @Expose
    private Response response;
    @SerializedName("ToCities")
    @Expose
    private Object toCities;
    public final static Parcelable.Creator<D> CREATOR = new Creator<D>() {


        @SuppressWarnings({
                "unchecked"
        })
        public D createFromParcel(Parcel in) {
            return new D(in);
        }

        public D[] newArray(int size) {
            return (new D[size]);
        }

    }
            ;

    protected D(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.citiesList, (CitiesList.class.getClassLoader()));
        this.fromCities = ((Object) in.readValue((Object.class.getClassLoader())));
        this.response = ((Response) in.readValue((Response.class.getClassLoader())));
        this.toCities = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public D() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CitiesList> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<CitiesList> citiesList) {
        this.citiesList = citiesList;
    }

    public Object getFromCities() {
        return fromCities;
    }

    public void setFromCities(Object fromCities) {
        this.fromCities = fromCities;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Object getToCities() {
        return toCities;
    }

    public void setToCities(Object toCities) {
        this.toCities = toCities;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeList(citiesList);
        dest.writeValue(fromCities);
        dest.writeValue(response);
        dest.writeValue(toCities);
    }

    public int describeContents() {
        return 0;
    }

}