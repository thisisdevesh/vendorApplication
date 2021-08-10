package com.gts.coordinator.ui.booking.assigned.UnassignedBooking;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gts.coordinator.Adapter.BookingListAdepter;
import com.gts.coordinator.R;


public class BookingAssignFragment extends Fragment {
    private BookingViewModel bookingViewModel;
    private BookingListAdepter bookingListAdepter;
    private TextView errorMessasge;

    public BookingAssignFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_assign, container, false);
        bookingListAdepter = new BookingListAdepter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rev_booking_list);

            errorMessasge = view.findViewById(R.id.error_message);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bookingListAdepter);
            bookingViewModel = new ViewModelProvider(getActivity()).get(BookingViewModel.class);

            bookingViewModel.getBooking().observe(getActivity(), bookingLists -> {

                if (!bookingLists.isEmpty()) {
                    errorMessasge.setVisibility(View.GONE);
                    bookingListAdepter.setBookingData(bookingLists);
                    recyclerView.setVisibility(View.VISIBLE);


                }else {
                    errorMessasge.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }


            });
            return view;
        }
}
