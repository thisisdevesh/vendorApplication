package com.gts.coordinator.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.gts.coordinator.dao.CityList;

public class CityAdapter extends ArrayAdapter<CityList> {
    private Context context;

    public CityAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
