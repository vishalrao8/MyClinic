package com.unitedcreation.myclinic.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HospitalRecyclerAdapter extends RecyclerView.Adapter <HospitalRecyclerAdapter.ViewHolder> {
    String hospital_list[]=null;
    public HospitalRecyclerAdapter(String[] array){
        hospital_list=array;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mHospitalName.setText(hospital_list[position]);
        holder.mRatingBar.setRating(5-position);

    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mHospitalName;
        RatingBar mRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHospitalName=itemView.findViewById(R.id.card_hospital_name);
            mRatingBar=itemView.findViewById(R.id.card_rating);
        }
    }

    @Override
    public int getItemCount() {
        return hospital_list.length;
    }
}
