package com.unitedcreation.myclinic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.model.Appointment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorRecyclerAdapter extends RecyclerView.Adapter <DoctorRecyclerAdapter.ViewHolder> {

    private ArrayList<Appointment> appointments;

    public DoctorRecyclerAdapter(ArrayList<Appointment> appointments){
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameTv.setText(appointments.get(position).getName());
        holder.timeTv.setText(appointments.get(position).getTime());

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv, timeTv;
        ViewHolder(@NonNull View itemView) {

            super(itemView);
            nameTv = itemView.findViewById(R.id.doctor_patient_name);
            timeTv = itemView.findViewById(R.id.doctor_patient_time);

        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
