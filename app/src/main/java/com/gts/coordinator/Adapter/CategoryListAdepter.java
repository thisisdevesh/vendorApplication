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

public class CategoryListAdepter extends RecyclerView.Adapter<CategoryListAdepter.TestViewHolder>{
    private ArrayList<CategoryList> categoryLists;
    private Context context;

    public CategoryListAdepter(ArrayList<CategoryList> categoryLists, Context context) {
        this.categoryLists = categoryLists;
        this.context = context;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_test,parent,false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        CategoryList categoryList = categoryLists.get(position);

        holder.atual_cat.setText(categoryList.getActualCat());
        holder.date.setText(categoryList.getDate());
       /* for (int i = 0; i <position ; i++) {
            if (categoryList.getActualCat().equals(categoryList.getActualCat())){
                holder.atual_cat.setVisibility(View.GONE);
            }else {
                holder.atual_cat.setVisibility(View.VISIBLE);
            }
        }
        for (int i = 0; i <position ; i++) {
            if (categoryList.getDate().equals(categoryList.getDate())){
                holder.date.setVisibility(View.GONE);
            }else if (!categoryList.getDate().equals(categoryList.getDate())){
                holder.date.setVisibility(View.VISIBLE);
            }
        }*/

        holder.time.setText(categoryList.getTime());
        holder.cat_name.setText(categoryList.getCatName());


    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{
     TextView date,time,atual_cat,cat_name;
        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time3);
            atual_cat = itemView.findViewById(R.id.actual_cat);
            cat_name = itemView.findViewById(R.id.cate_name);




        }
    }


}
