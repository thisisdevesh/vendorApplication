package com.gts.coordinator.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gts.coordinator.Adapter.BookingReqAdapter;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.BookingRequestDao;

import java.util.ArrayList;


public class BookingRequestReport extends Fragment {
    private RecyclerView bookingREquestRecycler;
    // TODO: Rename and change types of parameters
    private ArrayList<BookingRequestDao> bookinReqDaos;
    private BookingReqAdapter bookingReqAdapter;
    private  TextView tvNoRecord;
    public BookingRequestReport() {
    }
    public static BookingRequestReport newInstance(String param1, String param2) {
        BookingRequestReport fragment = new BookingRequestReport();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookinReqDaos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_request_report, container, false);
        bookingREquestRecycler = (RecyclerView)view.findViewById(R.id.bkng_req_recycler);
        tvNoRecord = (TextView)view.findViewById(R.id.req_noRecords);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookingREquestRecycler.setLayoutManager(layoutManager);
        bookingREquestRecycler.setItemAnimator(new DefaultItemAnimator());
        bookingReqAdapter = new BookingReqAdapter(bookinReqDaos,tvNoRecord);
        bookingREquestRecycler.setAdapter(bookingReqAdapter);
        if (bookinReqDaos.size() ==0)
        {
            tvNoRecord.setVisibility(View.VISIBLE);
            tvNoRecord.setText(" No Records Found ");
        }
        else
        {
            tvNoRecord.setVisibility(View.GONE);
        }
        return view;
    }


    public void getBookingRequestStatus(ArrayList<BookingRequestDao> bookinReqDaos) {
        this.bookinReqDaos = bookinReqDaos;
       /* if (bookinReqDaos.size() ==0) {
            tvNoRecord.setVisibility(View.VISIBLE);
            tvNoRecord.setText(" No Records Found ");
        }
        else {
            tvNoRecord.setVisibility(View.GONE);
        }*/
        bookingReqAdapter = new BookingReqAdapter(bookinReqDaos,tvNoRecord);
        bookingREquestRecycler.setAdapter(bookingReqAdapter);

    }


}
