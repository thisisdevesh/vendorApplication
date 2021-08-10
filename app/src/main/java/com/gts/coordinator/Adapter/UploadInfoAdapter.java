package com.gts.coordinator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.ContractorData.UploadDocument.DocumentDetail;
import com.gts.coordinator.R;

import java.util.ArrayList;

public class UploadInfoAdapter extends RecyclerView.Adapter<UploadInfoAdapter.CabInfoViewHolder> {
        public ArrayList<DocumentDetail> document;
        public  tackImage tackImage;
        uploadImage uploadImage;
        public  Context context;
        private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    //create lisnner
    public void tackPic (tackImage tackImage){
        this.tackImage =tackImage;
    }
    public void postUploadImage(uploadImage uploadImage){
        this.uploadImage =uploadImage;
    }
    public UploadInfoAdapter(ArrayList<DocumentDetail> document, Context context) {
        this.document = document;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
        public int getItemCount() {
            return document == null ? 0 : document.size();
        }


        @Override
        public CabInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_upload_row, parent, false);
            CabInfoViewHolder cabInfoViewHolder = new CabInfoViewHolder(view,mListener);
            return cabInfoViewHolder;
        }

        @Override
        public void onBindViewHolder(CabInfoViewHolder holder, final int position) {
         DocumentDetail li = document.get(position);
         String doc_type = li.getDocument_type();
         long vacb_id = li.getVcabid();
         holder.tvDocTitle.setText("Select "+doc_type);
         holder.imgSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        tackImage.userData(doc_type,vacb_id,position,holder.imgSelected,holder.imgSend,holder.imgCheck,holder.progressBar);
                        //CropImage.startPickImageActivity((Activity) context);
                    }catch (Exception e){}
                }
            });
         //,holder.progressBar,holder.imgCheck,holder.imgSend
         holder.imgSend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 uploadImage.uploadImageStatus(position);
             }
         });

        }
    public class CabInfoViewHolder extends RecyclerView.ViewHolder {
            TextView tvDocTitle;
            ImageView imgSend, imgCheck, imgSelected;
            ProgressBar progressBar;
            RelativeLayout uploadPicLayout;
            public CabInfoViewHolder(View itemView,final OnItemClickListener listener) {
                super(itemView);
                tvDocTitle =  itemView.findViewById(R.id.doc_title);
                imgSend =  itemView.findViewById(R.id.upload_image);
                imgCheck =  itemView.findViewById(R.id.checkImage);
                imgSelected =  itemView.findViewById(R.id.selected_img);
                progressBar =  itemView.findViewById(R.id.process_bar_row);
                uploadPicLayout =  itemView.findViewById(R.id.doc_panel);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener !=null){
                            int position = getAdapterPosition();
                            if (position !=RecyclerView.NO_POSITION){
                                listener.onItemClick(position,document.get(position).getDocument_type(),document.get(position).getVcabid(),imgSelected,imgSend,imgCheck,progressBar);
                            }
                        }
                    }
                });

            }
        }

        public interface tackImage{
            void  userData(String doc_type, long vcab_id, int po, ImageView imageView, ImageView selected, ImageView imgSelected, ProgressBar progressBar);
        }
    public interface OnItemClickListener{
        void onItemClick(int position,String doc_type, long vcab_id,ImageView viewImageView, ImageView sendImage, ImageView resultImage, ProgressBar progressBar);
    }
        public interface uploadImage{
        void uploadImageStatus(int status);
        }


    }