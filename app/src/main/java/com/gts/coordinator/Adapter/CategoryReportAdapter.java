package com.gts.coordinator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.ContractorData.CategoryReport.CategoryList;
import com.gts.coordinator.R;

import java.util.ArrayList;

public class CategoryReportAdapter extends RecyclerView.Adapter<CategoryReportAdapter.CategoryReportViewHolder>  {
     ArrayList<CategoryList> categoryLists;
     Context context;

    public CategoryReportAdapter(ArrayList<CategoryList> categoryLists, Context context) {
        this.categoryLists = categoryLists;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_report,parent,false);
        return new CategoryReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryReportViewHolder holder, int position) {
         CategoryList list = categoryLists.get(position);
         holder.date.setText(list.getDate());
         holder.time.setText(list.getTime());
         holder.name.setText(list.getCatName());
         holder.actual_cat.setText(list.getActualCat());
    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class  CategoryReportViewHolder extends RecyclerView.ViewHolder{
      TextView date,time,name,actual_cat;

        public CategoryReportViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            actual_cat = itemView.findViewById(R.id.actual_cat);
            name = itemView.findViewById(R.id.cat_name);

        }
    }

}
