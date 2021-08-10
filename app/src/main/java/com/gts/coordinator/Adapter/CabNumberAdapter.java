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
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CabNumberAdapter extends ArrayAdapter<Driver> {
    private Context context;
    private List<Driver> cabNumberList;
    private List<Driver> cabNumberListAll;


    public CabNumberAdapter(Context context, ArrayList<Driver> numberList) {
        super(context,0,numberList);
        this.context = context;
      this.cabNumberList = numberList;
      cabNumberListAll = new ArrayList<>(numberList);

    }

    @Override
    public int getCount() {
      return cabNumberList.size();
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }
//


    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public int getItemViewType(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//      Log.i("****", "*********************");
      Driver driver = cabNumberList.get(position);
      LayoutInflater inflater = ((Activity) context).getLayoutInflater();
      View view = inflater.inflate(R.layout.custom_spinner, parent, false);
      TextView label = (TextView) view.findViewById(R.id.spinner_text);
      label.setText(driver.getCabNo());
     // vcabId = driver.getDriverId();
     // cabNumber = driver.getCabNo();

      return view;
    }
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Driver> suggestions = new ArrayList<Driver>();
                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(cabNumberListAll);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Driver item : cabNumberListAll) {
                        if (item.getName().toLowerCase().contains(filterPattern)||item.getPhoneNo().toLowerCase().contains(filterPattern)||item.getCabNo().toLowerCase().contains(filterPattern)) {
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
                Utils.showOkAlert(context,context.getString(R.string.error),"Item not Found",false);
            }

            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Driver) resultValue).getCabNo();
        }
    };

}
