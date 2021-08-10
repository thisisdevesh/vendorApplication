package com.gts.coordinator.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.SelfServicePkgDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SelfServPkgAdapter extends RecyclerView.Adapter<SelfServPkgAdapter.SelfServViewHolder> {
       private ArrayList<SelfServicePkgDao> selfServPkgList;
       private SimpleDateFormat timeFormatter ;
       private Context context;

//    SimpleDateFormat dateFormatter ;

        public SelfServPkgAdapter(ArrayList<SelfServicePkgDao> selfServPkgList,Context context) {
            this.selfServPkgList = selfServPkgList;
            this.context=context;
            timeFormatter = new SimpleDateFormat("HH:mm");

        }

        @Override
        public SelfServViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.self_service_pkg_veiw, parent, false);
            return new SelfServViewHolder(view);

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(SelfServViewHolder holder, int position) {
            SelfServicePkgDao selfServicePkgDao = selfServPkgList.get(position);
            Calendar cal = selfServicePkgDao.getDate();
            String strDate = String.format("%s-%s-%s", cal.get(Calendar.DAY_OF_MONTH), cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()), cal.get(Calendar.YEAR)) ;
            Log.i("SelfServicePkgReport","SelfServPkgAdapter:onBindViewHolder():strDate="+strDate);
            holder.date.setText(strDate);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            holder.rv.setLayoutManager(layoutManager);
            holder.rv.setItemAnimator(new DefaultItemAnimator());
            ArrayList<SelfServicePkgDao.PkgAttemptDetails> pkgAttempts = selfServicePkgDao.getPkgAttempts();
            Log.i("SelfServicePkgReport","SelfServPkgAdapter:onBindViewHolder():pkgAttempts="+pkgAttempts);
            holder.rv.setAdapter(new AttemptDetailAdapter(pkgAttempts));
        }

        @Override
        public int getItemCount() {
            return selfServPkgList==null? 0 : selfServPkgList.size() ;
        }

        public class SelfServViewHolder extends RecyclerView.ViewHolder {
            TextView date ; //, time, plantype;
            RecyclerView rv ;
//      CardView seperator;

            public SelfServViewHolder(View itemView) {
                super(itemView);
                date =  itemView.findViewById(R.id.tv_selfserv_date);
                rv =  itemView.findViewById(R.id.attempt_details);
            }

        }

      public   class AttemptDetailAdapter extends RecyclerView.Adapter<AttemptDetailAdapter.AttemptViewHolder> {
            ArrayList<SelfServicePkgDao.PkgAttemptDetails> pkgAttemptList;

            public AttemptDetailAdapter(ArrayList<SelfServicePkgDao.PkgAttemptDetails> pkgAttemptList) {
                this.pkgAttemptList = pkgAttemptList;
            }

            @Override
            public AttemptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.self_service_pkg_attempt_item, parent, false);
                return new AttemptViewHolder(view);
            }

            @Override
            public void onBindViewHolder(AttemptViewHolder holder, int position) {
                SelfServicePkgDao.PkgAttemptDetails pkgAttemptDetails = pkgAttemptList.get(position);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                holder.attemptRv.setLayoutManager(layoutManager);
                holder.attemptRv.setItemAnimator(new DefaultItemAnimator());
                String strTime = timeFormatter.format(pkgAttemptDetails.getTime().getTime()) ;
                holder.attemptRv.setAdapter(new PkgDetailAdapter(pkgAttemptDetails.getPackages(), strTime ) );
            }

            @Override
            public int getItemCount() {
                return pkgAttemptList==null? 0 : pkgAttemptList.size() ;
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            public class AttemptViewHolder extends RecyclerView.ViewHolder {
                RecyclerView attemptRv;
                public AttemptViewHolder(View itemView) {
                    super(itemView);
                    attemptRv =  itemView.findViewById(R.id.attempt_pkg_details);
                }
            }

            class PkgDetailAdapter extends RecyclerView.Adapter<PkgDetailAdapter.PkgViewHolder> {
                private ArrayList<String> pkgNames ;
                private String time;

                public PkgDetailAdapter(ArrayList<String> pkgNames, String time) {
                    this.pkgNames = pkgNames;
                    this.time = time;
                }

                @Override
                public PkgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.self_service_pkg_item, parent, false);
                    return new PkgViewHolder(view);
                }

                @Override
                public void onBindViewHolder(PkgViewHolder holder, int position) {
                    String pkgName = pkgNames.get(position);
                    holder.tvPkg.setText(pkgName);
                    holder.tvTime.setText(time);
                }

                @Override
                public int getItemCount() {
                    return pkgNames==null ? 0 : pkgNames.size() ;
                }

                @Override
                public int getItemViewType(int position) {
                    return position;
                }

                public class PkgViewHolder extends RecyclerView.ViewHolder
                {
                    TextView tvPkg, tvTime ;
                    public PkgViewHolder(View itemView) {
                        super(itemView);
                        tvPkg =  itemView.findViewById(R.id.txt_pkg_name);
                        tvTime =  itemView.findViewById(R.id.txt_time);
                    }
                }

            }

        }
    }
