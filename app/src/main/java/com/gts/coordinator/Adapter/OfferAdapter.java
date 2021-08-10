package com.gts.coordinator.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gts.coordinator.Activity.OffersActivity;
import com.gts.coordinator.Model.ContractorData.offers.OffersModel;
import com.gts.coordinator.R;

import java.util.ArrayList;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private Context context;
    /*    List<String> models;*/
    private String imageUrl;

    public OfferAdapter(Context context, String imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*     Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(models.get(position).getImage()));
         *//*    Glide.with(context)
                .asBitmap()
                .load(bitmap)
             *//**//*   .placeholder(R.drawable.image2)*//**//*
                .into(holder.offerImage);
*/
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.offerImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView offerImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerImage = itemView.findViewById(R.id.offer_banner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent i = new Intent(context, OffersActivity.class);
                        context.startActivity(i);
                    }
                }
            });
        }
    }
}
