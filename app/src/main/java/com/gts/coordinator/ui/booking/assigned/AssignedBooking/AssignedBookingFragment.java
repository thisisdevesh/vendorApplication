package com.gts.coordinator.ui.booking.assigned.AssignedBooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gts.coordinator.Adapter.AssignBookingListAdepter;
import com.gts.coordinator.R;

public class AssignedBookingFragment extends Fragment {
    private AssignBookingListAdepter assignBookingListAdepter;
    private AcceptBookingViewModel viewModel;
    private TextView errorMessage;
    public AssignedBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accept_booking, container,false);
        RecyclerView recyclerView = view.findViewById(R.id.rev_accept_booking);
        errorMessage = view.findViewById(R.id.error_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        assignBookingListAdepter = new AssignBookingListAdepter(getActivity());
        recyclerView.setAdapter(assignBookingListAdepter);
        viewModel = new ViewModelProvider(getActivity()).get(AcceptBookingViewModel.class);


        viewModel.getAssignBooking().observe(getActivity(), assignLists -> {
            if (assignLists.isEmpty()) {
                if (errorMessage.getVisibility() == View.GONE)
                    errorMessage.setVisibility(View.VISIBLE);
            }else {
                if (errorMessage.getVisibility() == View.VISIBLE)
                    errorMessage.setVisibility(View.GONE);
            }
            assignBookingListAdepter.setBookingData(assignLists);

        });
        return view;
    }
}