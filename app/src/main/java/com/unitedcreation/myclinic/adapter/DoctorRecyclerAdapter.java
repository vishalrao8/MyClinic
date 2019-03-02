package com.unitedcreation.myclinic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorRecyclerAdapter extends RecyclerView.Adapter <DoctorRecyclerAdapter.ViewHolder> {
    String time[];
    public DoctorRecyclerAdapter(String[] t_array){
        time=t_array;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mHospitalName.setText("Patient "+position);
       holder.mTime.setText(time[position]);

    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mHospitalName,mTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHospitalName=itemView.findViewById(R.id.doctor_patient_name);
            mTime=itemView.findViewById(R.id.doctor_patient_time);
        }
    }

    @Override
    public int getItemCount() {
        return time.length;
    }
}
