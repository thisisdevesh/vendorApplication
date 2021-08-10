package com.gts.coordinator.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.ContractorData.WallteHistory.History;
import com.gts.coordinator.R;



public  class WalletHistoryAdapter extends PagedListAdapter<History, WalletHistoryAdapter.WalletViewHolder> {
  public   Context context;
    boolean firstTime = false;
    Update mListener;

    public WalletHistoryAdapter(Context context, Activity fragment) {
        super(DIFF_CALLBACK);
        this.context = context;
        mListener = (Update) fragment;

    }
    private static DiffUtil.ItemCallback<History> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<History>() {
                @Override
                public boolean areItemsTheSame(@NonNull History oldItem, @NonNull History newItem) {
                    return oldItem.getHistoryDetails().getId() == newItem.getHistoryDetails().getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull History oldItem, @NonNull History newItem) {

                    return oldItem.equals(newItem);
                }
            };


    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history_row, parent, false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        History info = getItem(position);
        double availBal = info.getHistoryDetails().getNetBalance();
        double creditedAmount = info.getHistoryDetails().getAmount();
        if (creditedAmount < 0) {
            holder.tvCredited.setText("Debited Amount");
            holder.reditedtextbox.setTextColor(context.getResources().getColor(R.color.dark_red));
        }else if (creditedAmount>0){
            holder.tvCredited.setText("Credit Amount");
            holder.reditedtextbox.setTextColor(context.getResources().getColor(R.color.dark_green));
        }
        if (availBal < 0) {
            holder.unning_balancetext.setTextColor(context.getResources().getColor(R.color.dark_red));
        }
        holder.ooking_idtextbox.setText(info.getHistoryDetails().getTransId());
        holder.reditedtextbox.setText(String.valueOf(info.getHistoryDetails().getAmount()));
        holder.ransationdatetext.setText(String.valueOf(info.getHistoryDetails().getTransDate()));
        holder.aymentsummarytext.setText(String.valueOf(info.getHistoryDetails().getPaymentSummary()));
        holder.unning_balancetext.setText(String.valueOf(info.getHistoryDetails().getNetBalance()));
        if(!firstTime){
            mListener.updateAvailableBalance(info.getHistoryDetails().getNetBalance());
            firstTime = true;
        }
    }



    public class WalletViewHolder extends RecyclerView.ViewHolder{
        TextView ooking_idtextbox, reditedtextbox, ransationdatetext, aymentsummarytext, unning_balancetext, tvCredited;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            ooking_idtextbox =  itemView.findViewById(R.id.booking_idtextbox);
            reditedtextbox =  itemView.findViewById(R.id.Creditedtextbox);
            tvCredited =  itemView.findViewById(R.id.Credited);
            ransationdatetext =  itemView.findViewById(R.id.Transationdatetext);
            aymentsummarytext =  itemView.findViewById(R.id.Paymentsummarytext);
            unning_balancetext =  itemView.findViewById(R.id.running_balancetext);
        }


    }

   public interface Update{
        void updateAvailableBalance(double amount);
    }

}
