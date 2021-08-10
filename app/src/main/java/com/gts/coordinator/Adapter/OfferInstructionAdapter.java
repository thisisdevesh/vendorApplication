package com.gts.coordinator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.ContractorData.Bonus.Instructionlist;
import com.gts.coordinator.R;

import java.util.List;

public class OfferInstructionAdapter extends RecyclerView.Adapter<OfferInstructionAdapter.ViewHolder> {


    private List<Instructionlist> instructionlists;
    private Context context;

    public OfferInstructionAdapter(Context context, List<Instructionlist> instructionlists) {
        this.context = context;
        this.instructionlists = instructionlists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer_instruction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.instruction.setText(instructionlists.get(position).getInstructionlist());
    }

    @Override
    public int getItemCount() {
        return instructionlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView instruction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instruction = itemView.findViewById(R.id.offer_inst);
        }
    }
}
