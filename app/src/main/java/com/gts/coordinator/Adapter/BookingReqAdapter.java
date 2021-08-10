package com.gts.coordinator.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.BookingRequestDao;

import java.util.ArrayList;

public class BookingReqAdapter extends RecyclerView.Adapter<BookingReqAdapter.BookingReqHolder> {
        private TextView textView;
        ArrayList<BookingRequestDao> sessionDaos;

        public BookingReqAdapter(ArrayList<BookingRequestDao> sessionDaos,TextView textView) {
            this.sessionDaos = sessionDaos;
            this.textView=textView;
        }

        @Override
        public BookingReqHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_req_view_holder,parent,false);
            return new BookingReqHolder(view);
        }

        @Override
        public void onBindViewHolder(BookingReqHolder holder, int position) {
            BookingRequestDao bookingRequestDao = sessionDaos.get(position);
            if (sessionDaos.size()<=0) {
               textView.setVisibility(View.VISIBLE);
               textView.setText("No Records Found ");
            }else {
                textView.setVisibility(View.GONE);
            }

//      Log.i("Values are "," "+bookingRequestDao.getAccepted()+""+bookingRequestDao.getAccepted());
            holder.accepted.setText(bookingRequestDao.getAccepted());
            holder.notConsidered.setText(bookingRequestDao.getNotConsidered());
            holder.notResponded.setText(bookingRequestDao.getNotResponded());
            holder.rejected.setText(bookingRequestDao.getRedjected());
            holder.date.setText(bookingRequestDao.getDate());
//      Log.i("BookingRequestReport","---size is ******************** = "+sessionDaos.size());
        }

        @Override
        public int getItemCount() {
            return sessionDaos.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return position;
        }

        public  class BookingReqHolder extends RecyclerView.ViewHolder
        {
            TextView accepted, notConsidered,notResponded,rejected,date,noRecords;

            public BookingReqHolder(View itemView) {
                super(itemView);
                accepted = (TextView)itemView.findViewById(R.id.tv_accepted);
                notConsidered = (TextView)itemView.findViewById(R.id.tv_not_considerd);
                notResponded = (TextView)itemView.findViewById(R.id.tv_not_responded);
                rejected = (TextView)itemView.findViewById(R.id.tv_rejected);
                date = (TextView)itemView.findViewById(R.id.tv_date);
            }
        }


        /**/

    }
