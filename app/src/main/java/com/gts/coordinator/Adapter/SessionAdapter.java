package com.gts.coordinator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.SessionDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private ArrayList<SessionDao> sessionDaos;
    private ProgressBar progressBar;
     private Calendar prevTime;
     private TextView textView;
/*
    public SessionAdapter(Context context, ArrayList<SessionDao> sessionDaos) {


    }
*/


  public SessionAdapter(Context activity, ArrayList<SessionDao> sessionDaos, Calendar prevTime, ProgressBar progressBar,TextView textView) {
    this.sessionDaos = sessionDaos;
    this.prevTime =prevTime;
    this.progressBar=progressBar;
    this.textView =textView;
  }

  @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lgoin_out_row_view, parent, false);

      return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {

       if ( sessionDaos.size()!=0){
         progressBar.setVisibility(View.GONE);
         textView.setVisibility(View.GONE);
       }else {
         progressBar.setVisibility(View.VISIBLE);
       }
      SessionDao sessionDao = sessionDaos.get(position);
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      Calendar cal = sessionDao.getTime();
      Date date = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
      if (position > 0) {
        SessionDao previousSession = sessionDaos.get(position - 1);
        Calendar prevCal = previousSession.getTime();
        Date prevDate = new Date(prevCal.get(Calendar.YEAR), prevCal.get(Calendar.MONTH), prevCal.get(Calendar.DAY_OF_MONTH));
        if (previousSession.getLoginLoutStatus().equals("Login") && sessionDao.getLoginLoutStatus().equals("Logout")) {
          if (prevTime == null) {
            long minuteValue;
            long miliSecondForDate1 = prevCal.getTimeInMillis();
            long miliSecondForDate2 = cal.getTimeInMillis();
            long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
            long diffInMinute = diffInMilis / (60 * 1000);
            long diffInHour = diffInMilis / (60 * 60 * 1000);
            if (diffInMinute > 60) {
              minuteValue = diffInMinute % 60;
            } else {
              minuteValue = diffInMinute;
            }
            String totalTime = String.format("%s : %s", diffInHour + " HR", minuteValue + " min");
            holder.totalTime.setVisibility(View.VISIBLE);
            holder.totalTime.setText(totalTime);
          } else {
            long minuteValue;
            long miliSecondForDate1 = prevTime.getTimeInMillis();
            long miliSecondForDate2 = cal.getTimeInMillis();
            long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
            long diffInMinute = diffInMilis / (60 * 1000);
            long diffInHour = diffInMilis / (60 * 60 * 1000);
            if (diffInMinute > 60) {
              minuteValue = diffInMinute % 60;
            } else {
              minuteValue = diffInMinute;
            }
            String totalTime = String.format("%s : %S", diffInHour + " HR", minuteValue + " min");
            holder.totalTime.setVisibility(View.VISIBLE);
            holder.totalTime.setText(totalTime);
            prevTime = null;
          }

        } else if (previousSession.getLoginLoutStatus().equals("Login") && sessionDao.getLoginLoutStatus().equals("Login")) {
          if (prevTime == null) {
            prevTime = previousSession.getTime();
          }
        }
        if (date.after(prevDate)) {
          holder.date.setVisibility(View.VISIBLE);
          holder.test_devider.setVisibility(View.VISIBLE);
//          holder.date.setCabNumbers(formatter.format(date));
          holder.date.setText(String.format("%s-%s-%s", cal.get(Calendar.DAY_OF_MONTH), cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()), cal.get(Calendar.YEAR)));
        }

      } else {
        holder.date.setVisibility(View.VISIBLE);
        holder.test_devider.setVisibility(View.VISIBLE);
//        holder.date.setCabNumbers(formatter.format(date));
        holder.date.setText(String.format("%s-%s-%s", cal.get(Calendar.DAY_OF_MONTH), cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()), cal.get(Calendar.YEAR)));

      }

      holder.status.setText(sessionDao.getLoginLoutStatus());
      String strTime = String.format("%s:%s", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
//      holder.time.setCabNumbers(new SimpleDateFormat("HH:mm").format(date));
      holder.time.setText(strTime);


    }

    @Override
    public int getItemViewType(int position) {
      return position;
    }

    @Override
    public int getItemCount() {
      return sessionDaos.size();
    }

    class SessionViewHolder extends RecyclerView.ViewHolder {
      private TextView date, time, status, totalTime, noRecords, test_devider;


      public SessionViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.tv_date);
        status = itemView.findViewById(R.id.tv_status);
        time = itemView.findViewById(R.id.time);
        totalTime = itemView.findViewById(R.id.total_time);
        test_devider = itemView.findViewById(R.id.test_devaider);

      }
    }

  }
