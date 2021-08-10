package com.gts.coordinator.dao;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MarkerItems implements ClusterItem {
    private final LatLng mPosition;
    private String name;
    private String cabNumbers;
    private String phoneNumber;
    private  int status;
//    private  Bitmap image;

//    public MarkerItems(double lat, double lng, String name, String cabNumbers,String phoneNumber ,int status)
    public MarkerItems(LatLng position, String name, String cabNumbers,String phoneNumber ,int status) {
//        mPosition = new LatLng(lat, lng);
        mPosition = position;
        this.name = name;
        this.cabNumbers = cabNumbers;
        this.phoneNumber = phoneNumber;
        this.status = status;
//        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCabNumbers(String cabNumbers) {
        this.cabNumbers = cabNumbers;
    }

    public LatLng getmPosition() {
        return mPosition;
    }

    public String getName() {
        return name;
    }

    public String getCabNumbers() {
        return cabNumbers;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return cabNumbers;
    }

    public int getStatus() {
        return status;
    }
}
