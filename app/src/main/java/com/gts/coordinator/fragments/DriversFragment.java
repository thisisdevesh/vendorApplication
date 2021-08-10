package com.gts.coordinator.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.DriverDBInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DriversFragment.OnBackClickListener} interface
 * to handle interaction events.
 * Use the {@link DriversFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriversFragment extends CommonFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_VENDOR = "vendor";

    private static DriversFragment driversFragment ;
    // TODO: Rename and change types of parameters
    private Vendor vendor ;
    private TextView tvVendorName ;
    private ImageView imgBack ;

    private RecyclerView rvDrivers ;
    private List<Driver> driverList;
    private DriverListAdapter driversAdapter ;
    private OnBackClickListener mListener;

    public DriversFragment() {
        // Required empty public constructor
    }

    /**
     * To instantiate DriversFragment.
     * @param vendor Selected Vendor ( from previous vendor's list).
     * @return A new instance of fragment DriversFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriversFragment newInstance(Vendor vendor) {
//        if (driversFragment==null) {
            driversFragment = new DriversFragment();
        if (vendor==null) {
            Bundle args = new Bundle();
            args.putParcelable(ARG_VENDOR, vendor);
            driversFragment.setArguments(args);
        }
//        }else{
//            driversFragment.setVendorId(vendorId);
//        }
        return driversFragment;
    }

    public static DriversFragment newInstance() {
        return newInstance(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments() ;
        if ( args != null) {
            vendor = args.getParcelable(ARG_VENDOR);
        }
        if (vendor!=null) {
            driverList = DriverDBInfo.getDrivers(getContext(), vendor.getVndId());
        }
        if (driversAdapter == null) {
            driversAdapter = new DriverListAdapter(getActivity(), driverList);
        }
        Log.i("DriversFragment", "onCreate():Adapter created. vendor="+vendor);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("DriversFragment", "onCreateView():vendor="+vendor);
        View view = inflater.inflate( R.layout.fragment_drivers, container, false );
        imgBack = view.findViewById( R.id.btn_back );
        tvVendorName = view.findViewById( R.id.vendor_name );
        rvDrivers = view.findViewById( R.id.driver_list ) ;
        rvDrivers.setAdapter(driversAdapter);
        imgBack.setOnClickListener(this);
        return view ;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackClickListener) {
            mListener = (OnBackClickListener) context;
        } else {
            throw new RuntimeException(String.format("%s must implement OnFragmentInteractionListener",context.toString()) );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setVendor(Vendor vendor){
        this.vendor = vendor ;
        Log.i("DriversFragment","setVendor():vendor="+vendor);
        if (vendor!=null) {
            tvVendorName.setText(vendor.getName());
            if (driverList!=null) {
                driverList.clear();
            }
            driverList = DriverDBInfo.getDrivers(getContext(), vendor.getVndId());
            driversAdapter.setDriverList(driverList);
        }
        Log.i("DriversFragment","Got Driver List:driverList="+driverList.size());
        Log.i("DriversFragment","Got Driver List:driverList="+driverList);
        driversAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view == imgBack){
            mListener.setVendorPage();
        }
    }

    @Override
    public void onDestroy() {
        Log.i("DriversFragment","onDestroy()");
        super.onDestroy();
    }

    private static class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.DriverViewHolder>{
        private Context context;
        private List<Driver> driverList;

        public DriverListAdapter(Context context, List<Driver> driverList)
//        public DriverListAdapter(Context context, Vendor vendor )
        {
            Log.i("DriversFragment", "DriverListAdapter():driverList=driverList");
            this.context = context;
            this.driverList = driverList;
//            if (vendor!=null) {
//                driverList = DriverDBInfo.getDrivers(context, vendor.getVndId());
//            }
        }

        void setDriverList( List<Driver> driverList ){
            this.driverList = driverList;
        }

        @NonNull
        @Override
        public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.i("DriversFragment", "DriverListAdapter:onCreateViewHolder()");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_driver_item, parent, false);
            return new DriverViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
            final Driver driver = driverList.get(position);
            final String phNo = driver.getPhoneNo();
            Log.i("DriversFragment", "DriverListAdapter:onBindViewHolder():driver="+driver);
            holder.driverName.setText(driver.getName());
            holder.phoneNo.setText(driver.getPhoneNo());
            holder.cabNumber.setText(driver.getCabNo());
            holder.category.setText(driver.getCategory());
            holder.cabName.setText(driver.getCabName());
            holder.descriptionPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("VendorsFragment","VendorListAdapterViewE:onBindViewHolder():driver="+driver);
                }
            });

            if (driver.getStatus() == 5) {
                holder.callingImage.setImageDrawable(getImage(R.drawable.call_black));
                holder.driverColor.setBackgroundColor(getColor(R.color.deactivated_color));
                holder.driverName.setTextColor(getColor(R.color.black));
            } else if (driver.getStatus() == 1) {
                holder.callingImage.setImageDrawable(getImage(R.drawable.call_white));
                holder.driverColor.setBackgroundColor(getColor(R.color.login_color));
                holder.driverName.setTextColor(getColor(R.color.white));

            } else if (driver.getStatus() == 2) {
                Drawable image = getImage(R.drawable.call_white);
                holder.callingImage.setImageDrawable(image);
                holder.driverColor.setBackgroundColor(getColor(R.color.busy_color));

                holder.driverName.setTextColor(getColor(R.color.white));


            } else {
                holder.callingImage.setImageDrawable(getImage(R.drawable.call_white));
                holder.driverColor.setBackgroundColor(getColor(R.color.logout_color));
                holder.driverName.setTextColor(getColor(R.color.white));
            }

            if (driver.isVerified()) {
                holder.tvVerifiedStatus.setVisibility(View.GONE);
            }

            holder.driverColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//         Log.i("DriverListFragment"," *********ONclick phoneNO = "+phNO);
                   //  Utils.callContact(context, phNo, VendorsActivity.CALL_REQUEST);
                }

            });
        }

        @Override
        public int getItemCount() {
            return driverList==null ? 0 : driverList.size();
        }

        private Drawable getImage(int resource){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                return context.getResources().getDrawable(resource);
            }else{
                return context.getDrawable(resource);
            }
        }

        private int getColor(int resource){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                return context.getResources().getColor(resource);
            }else{
                return context.getColor(resource);
            }
        }

        class DriverViewHolder extends RecyclerView.ViewHolder{
            TextView phoneNo, driverName, cabNumber, category, cabName, tvVerifiedStatus;
            ImageView callingImage;
            LinearLayout driverColor, drivercalling, descriptionPanel; //
            public DriverViewHolder(View itemView) {
                super(itemView);
                driverName = itemView.findViewById(R.id.image_view);
                phoneNo = itemView.findViewById(R.id.phoneNO);
                tvVerifiedStatus = itemView.findViewById(R.id.verified_status);
                driverColor = itemView.findViewById(R.id.driverColor);
                cabNumber = itemView.findViewById(R.id.cabNumber);
                cabName = itemView.findViewById(R.id.cabName);
                descriptionPanel = (LinearLayout) itemView.findViewById(R.id.description_panel);
                category = itemView.findViewById(R.id.cabCategory);
                callingImage = itemView.findViewById(R.id.driverCallImg);
                drivercalling = itemView.findViewById(R.id.drvCallingLay);
            }
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBackClickListener {
        void setVendorPage();
    }

    public interface DriverSelectionListener {
        void onDriverSelect(Driver driver);
    }

}
