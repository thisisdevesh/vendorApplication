package com.gts.coordinator.Adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class WalletActivityAdepter extends ArrayAdapter<Driver> {
  //  private Context context;
    private TextView text_item_notfound;
    private List<Driver> vendorsList;
    private List<Driver> vendorNameList;
    public WalletActivityAdepter(Context context, ArrayList<Driver> vendorsList, TextView text_item_notfound) {
        super(context,0,vendorsList);
      //  this.context = context;
        this.vendorsList = vendorsList;
        this.text_item_notfound = text_item_notfound;
        vendorNameList =new ArrayList<>(vendorsList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }
    @Override
    public int getCount() {
        return vendorsList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner2, parent, false);
        Driver vendor = vendorsList.get(position);
        //LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        TextView label =  view.findViewById(R.id.spinner_text2);
        TextView number =  view.findViewById(R.id.v_mobile_no);
        label.setText(vendor.getName());
        number.setText(vendor.getCabNo());
        //vendorID = vendor.getVndId();
        return view;
    }
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Driver> suggestions = new ArrayList<Driver>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(vendorsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Driver item : vendorNameList) {
                    if (item.getName().toLowerCase().contains(filterPattern)||item.getCabNo().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                        //
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            if (((List) results.values).size()==0){
                text_item_notfound.setVisibility(View.VISIBLE);
            }else {
                text_item_notfound.setVisibility(View.GONE);
            }
            notifyDataSetChanged();

        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Driver) resultValue).getCabNo();
        }
    };
}
