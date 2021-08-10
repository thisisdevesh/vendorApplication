package com.gts.coordinator.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdapterVendorNameList extends ArrayAdapter<Vendor>  {
private List<Vendor> vendorList;
private Context context;
private OnItemClickListener2 clickListener2;

    public interface OnItemClickListener2{
        void onItemClick(long position,String name);

    }

    public AdapterVendorNameList(@NonNull Context context, @NonNull List<Vendor> countryList,OnItemClickListener2 clickListener2) {
        super(context, 0, countryList);
        vendorList = new ArrayList<>(countryList);
        this.context =context;
        this.clickListener2=clickListener2;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner2, parent, false);
         final Vendor vendor = vendorList.get(position);

        final TextView label = (TextView) view.findViewById(R.id.spinner_text2);
        TextView number = (TextView) view.findViewById(R.id.v_mobile_no);
        label.setText(vendor.getName());
        number.setText(vendor.getPhno());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener2.onItemClick(vendor.getVndId(),vendor.getName());
              //  label.setText(vendor.getName());
            }
        });
        //vendorID = vendor.getVndId();
        return view;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Vendor> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(vendorList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Vendor item : vendorList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
              if (suggestions.size()==0){

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
                Utils.showOkAlert(context,context.getString(R.string.error),"Item not Found",false);
            }

            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Vendor) resultValue).getName();
        }
    };


}
