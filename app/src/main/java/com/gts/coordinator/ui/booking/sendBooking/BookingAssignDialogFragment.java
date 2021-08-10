package com.gts.coordinator.ui.booking.sendBooking;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gts.coordinator.Model.ContractorData.activeDriver.DriverDatum;
import com.gts.coordinator.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingAssignDialogFragment extends DialogFragment {
    private static final String TAG = "MyTag";
    long vendorId;
    String bookingId,cabNo;
    int bookingRqT;
    TextView item_not_found;
    ProgressBar progressBar;
    private List<DriverDatum> myList;
    private ActiveDriverListViewModel viewModel;
    private AdepterActiveDriverList adepterActiveDriverList;
    public BookingAssignDialogFragment() {
        // Required empty public constructor
    }
    public static BookingAssignDialogFragment newInstance(String booking_id, long vendorId,int bookingReuqestType,String cabNo) {
        BookingAssignDialogFragment fragment = new BookingAssignDialogFragment();
        Bundle args = new Bundle();
        args.putString("booking_id", booking_id);
        args.putLong("vendor_id", vendorId);
        args.putInt("booking_r_t",bookingReuqestType);
        args.putString("cab_no",cabNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        Bundle arg = getArguments();
        vendorId = arg.getLong("vendor_id");
        bookingId = arg.getString("booking_id");
        bookingRqT = arg.getInt("booking_r_t");
        cabNo = arg.getString("cab_no");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_assign_dialog, container, false);

        return view;
    }

    public void show(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
//        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        android.app.Fragment prev = fm.findFragmentByTag("MsgAlert");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        super.show(ft, "MsgAlert");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rev_active_driver);
        SearchView searchView = view.findViewById(R.id.searchDriver);
        item_not_found = view.findViewById(R.id.item_not_found);
        progressBar = view.findViewById(R.id.process_bar);
        // used for search view auto open
        searchView.onActionViewExpanded();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider((FragmentActivity) getActivity()).get(ActiveDriverListViewModel.class);

        viewModel.getActiveDrivers(vendorId, bookingId,cabNo,bookingRqT).observe((LifecycleOwner) getActivity(), response -> {
            myList =  response.body().getDriverData();
            if (progressBar.getVisibility()==View.VISIBLE)
                progressBar.setVisibility(View.GONE);
           if (myList.isEmpty()) {
               if (item_not_found.getVisibility() == View.GONE)
                   item_not_found.setVisibility(View.VISIBLE);
                   }
                adepterActiveDriverList = new AdepterActiveDriverList(getActivity(), response.body().getDriverData(),getDialog(),bookingId,bookingRqT);
                recyclerView.setAdapter(adepterActiveDriverList);
        });
        viewModel.getErrorMessage().observe((LifecycleOwner) getActivity(), s->{
            if (s!=null){
                TastyToast.makeText(getActivity(),s,TastyToast.LENGTH_LONG,TastyToast.INFO).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (myList.isEmpty()) {
                    TastyToast.makeText(getActivity(), "Driver Not Available ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else {
                    adepterActiveDriverList.getFilter().filter(newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            adepterActiveDriverList.notifyDataSetChanged();
            return false;
        });
        view.findViewById(R.id.fragment_back).setOnClickListener(v -> {
            getDialog().dismiss();
        });

    }
}
