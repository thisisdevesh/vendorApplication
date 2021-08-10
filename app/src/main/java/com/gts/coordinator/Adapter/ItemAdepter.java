package com.gts.coordinator.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ItemAdepter extends RecyclerView.Adapter<ItemAdepter.ItemViewHolder> implements Filterable {
private List<Driver> itemLists;
private List<Driver> itemListsAll;
private Context context;
    private OnItemClickListener mListener;
    public ItemAdepter(List<Driver> itemLists, Context context) {
        this.itemLists = itemLists;
        this.context = context;
       // this.mListener =(mListener)context;
        notifyDataSetChanged();
        itemListsAll = new ArrayList<>(itemLists);
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner2,parent,false);
        ItemViewHolder vh =new ItemViewHolder(view,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
       Driver list =itemLists.get(position);
       holder.name.setText(list.getCabNo());
       holder.rono.setText(String.valueOf(list.getName()));
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Driver> su =new ArrayList<Driver>();
                if (constraint==null || constraint.length()==0){
                    su.addAll(itemListsAll);
            //        Toast.makeText(context, "No Item Found", Toast.LENGTH_SHORT).show();
                }else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Driver list :itemListsAll){
                        if (list.getCabNo().toLowerCase().contains(filterPattern)||list.getName().toLowerCase().contains(filterPattern)){
                            su.add(list);

                        }else {
                            Utils.showOkAlert(context,context.getString(R.string.error),"Item not Found",false);
                        }
                    }

                }

            results.values = su;
          //  results.count = su.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
             itemLists.clear();
             itemLists.addAll((List) results.values);
             notifyDataSetChanged();

            }
        };
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView name,rono;
        public ItemViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.spinner_text2);
            rono =itemView.findViewById(R.id.v_mobile_no);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
//                    if (listener !=null){
//                        int position = getAdapterPosition();
//                        if (position !=RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
                }
            });


        }
    }
}
