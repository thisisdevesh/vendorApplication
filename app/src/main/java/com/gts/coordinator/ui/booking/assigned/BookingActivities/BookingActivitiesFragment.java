package com.gts.coordinator.ui.booking.assigned.BookingActivities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gts.coordinator.Adapter.BookingActivitiesListAdapter;
import com.gts.coordinator.R;

public class BookingActivitiesFragment extends Fragment {

    private RecyclerView bookingActivitiesRV;
    private BookingActivitiesListAdapter bookingActivitiesListAdapter;
    private BookingActivitiesListViewModel bookingActivitiesListViewModel;
    private static String TAG = "BookingActivitiesFragment";

    //widgits
    private TextView errorMessasge;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_activities, container, false);
        bookingActivitiesListAdapter = new BookingActivitiesListAdapter(getActivity());
        bookingActivitiesRV = view.findViewById(R.id.rev_bookingActivities_list);
        errorMessasge = view.findViewById(R.id.error_message);
        bookingActivitiesRV.setHasFixedSize(true);
        bookingActivitiesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookingActivitiesRV.setAdapter(bookingActivitiesListAdapter);
        bookingActivitiesListViewModel = new ViewModelProvider(getActivity()).get(BookingActivitiesListViewModel.class);

        bookingActivitiesListViewModel.getBookingActivitiesList().observe(getActivity(), assignLists ->{

            if (!assignLists.isEmpty()) {
                errorMessasge.setVisibility(View.GONE);
                bookingActivitiesListAdapter.setBookingData(assignLists);
                bookingActivitiesRV.setVisibility(View.VISIBLE);

            }else {
                errorMessasge.setVisibility(View.VISIBLE);
                bookingActivitiesRV.setVisibility(View.GONE);
            }
        });
        return view;
    }

}