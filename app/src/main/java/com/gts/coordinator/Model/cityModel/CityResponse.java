
package com.gts.coordinator.Model.cityModel;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResponse implements Parcelable
{

    @SerializedName("d")
    @Expose
    private D d;
    public final static Parcelable.Creator<CityResponse> CREATOR = new Creator<CityResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CityResponse createFromParcel(Parcel in) {
            return new CityResponse(in);
        }

        public CityResponse[] newArray(int size) {
            return (new CityResponse[size]);
        }

    }
            ;

    protected CityResponse(Parcel in) {
        this.d = ((D) in.readValue((D.class.getClassLoader())));
    }

    public CityResponse() {
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(d);
    }

    public int describeContents() {
        return 0;
    }

}