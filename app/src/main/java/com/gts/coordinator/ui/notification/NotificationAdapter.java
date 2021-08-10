package com.gts.coordinator.ui.notification;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.gts.coordinator.Notification.Model.NotificationList;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.paging.NetworkState;
import com.gts.coordinator.paging.NetworkStateItemViewHolder;

public class NotificationAdapter extends PagedListAdapter<NotificationList, RecyclerView.ViewHolder> {
   public Context mCtx;
    event mListener;
    private NetworkState networkState;
    private boolean status;
    private RetrofitApiInterface apiInterface;
    public NotificationAdapter(Context mCtx, Activity fragment) {
        super(NotificationList.DIFF_CALLBACK);
        this.mCtx = mCtx;
        mListener = (event) fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.notification_adapter) {
            View view = layoutInflater.inflate(R.layout.notification_adapter, parent, false);
            NotificatioItemViewHolder viewHolder = new NotificatioItemViewHolder(view);
            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            View view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position<0){
            mListener.hideShimmer();
        }else {}
        switch (getItemViewType(position)) {
            case R.layout.notification_adapter:
                mListener.hideShimmer();
                ((NotificatioItemViewHolder) holder).bindTo(getItem(position),mCtx);
                break;
            case R.layout.network_state_item:
              //  mListener.hideShimmer();
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }

    }
    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.notification_adapter;
        }
    }

   public interface event{
        void hideShimmer();
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


}





