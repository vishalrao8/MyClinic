package com.unitedcreation.myclinic.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PatientRecyclerAdapter extends RecyclerView.Adapter <PatientRecyclerAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mHospitalName.setText("Doctor "+ position);
        holder.mRatingBar.setRating(Math.abs(5-position));

    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mHospitalName;
        RatingBar mRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHospitalName=itemView.findViewById(R.id.patient_doctor_name);
            mRatingBar=itemView.findViewById(R.id.patient_doctor_rating);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
