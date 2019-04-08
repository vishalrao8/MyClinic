package com.unitedcreation.myclinic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.model.Appointment;
import com.unitedcreation.myclinic.utils.FireBaseUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.unitedcreation.myclinic.utils.StringUtils.APPOINTMENTS;
import static com.unitedcreation.myclinic.utils.StringUtils.APPROVED;
import static com.unitedcreation.myclinic.utils.StringUtils.PENDING;
import static com.unitedcreation.myclinic.utils.StringUtils.REJECTED;

public class ApprovedAppointmentRecyclerAdapter extends RecyclerView.Adapter <ApprovedAppointmentRecyclerAdapter.ViewHolder> {

    private ArrayList<Appointment> appointments;
    private ArrayList<String> appointmentskeylist ;
    private final DatabaseReference databaseReference= new FireBaseUtils().getDatabaseReference().child(APPOINTMENTS);

    public ApprovedAppointmentRecyclerAdapter(ArrayList<Appointment> appointments, ArrayList<String> appointmentskeylist){
        this.appointments = appointments;
        this.appointmentskeylist=appointmentskeylist;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int x=position;
        holder.nameTv.setText(appointments.get(position).getPname());
        holder.timeTv.setText(appointments.get(position).getTime());
        holder.dateTv.setText(appointments.get(position).getDate());
        switch (appointments.get(position).getStatus())
        {
            case PENDING:
                holder.mStatusIv.setImageResource(R.drawable.pending);
                break;
            case APPROVED:
                holder.mStatusIv.setImageResource(R.drawable.accept);
                break;
            case REJECTED:
                holder.mStatusIv.setImageResource(R.drawable.reject);

        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv, timeTv, dateTv;
        ImageView mStatusIv;
        ViewHolder(@NonNull View itemView) {

            super(itemView);
            nameTv = itemView.findViewById(R.id.appointment_patient_name);
            timeTv = itemView.findViewById(R.id.appointment_patient_time);
            dateTv=itemView.findViewById(R.id.appointment_patient_date);
            mStatusIv=itemView.findViewById(R.id.appointment_status_label);

        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
