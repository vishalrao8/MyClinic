package com.unitedcreation.myclinic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class DoctorRecyclerAdapter extends RecyclerView.Adapter <DoctorRecyclerAdapter.ViewHolder> {

    private ArrayList<Appointment> appointments;
    private ArrayList<String> appointmentskeylist ;
    final onButtonClick onButtonClick;
    private final DatabaseReference databaseReference= new FireBaseUtils().getDatabaseReference().child(APPOINTMENTS);

    public DoctorRecyclerAdapter(ArrayList<Appointment> appointments,ArrayList<String> appointmentskeylist,onButtonClick onButtonClick){
        this.appointments = appointments;
        this.appointmentskeylist=appointmentskeylist;
        this.onButtonClick=onButtonClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int x=position;
        holder.nameTv.setText(appointments.get(position).getPname());
        holder.timeTv.setText(appointments.get(position).getTime());
        holder.dateTv.setText(appointments.get(position).getDate());
        holder.mMainConst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mSelectionLayout.getVisibility()==View.VISIBLE)
                {
                    holder.mSelectionLayout.setVisibility(View.GONE);
                }
                else
                    {
                        holder.mSelectionLayout.setVisibility(View.VISIBLE);
                    }
            }
        });

        holder.mAcceptCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databaseReference.child(appointmentskeylist.get(x)).child("status").setValue(APPROVED);
               onButtonClick.onClicked();
            }
        });
        holder.mAcceptIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(appointmentskeylist.get(x)).child("status").setValue(APPROVED);
                onButtonClick.onClicked();
            }
        });
        holder.mRejectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(appointmentskeylist.get(x)).child("status").setValue(REJECTED);
                onButtonClick.onClicked();
            }
        });
        holder.mRejectCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(appointmentskeylist.get(x)).child("status").setValue(REJECTED);
                onButtonClick.onClicked();
            }
        });
    }

    public interface onButtonClick
    {
         void onClicked();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv, timeTv, dateTv;
        ConstraintLayout mMainConst;
        LinearLayout mSelectionLayout;
        CardView mAcceptCv,mRejectCv;
        ImageView mAcceptIv,mRejectIv;
        ViewHolder(@NonNull View itemView) {

            super(itemView);
            nameTv = itemView.findViewById(R.id.doctor_patient_name);
            timeTv = itemView.findViewById(R.id.doctor_patient_time);
            dateTv=itemView.findViewById(R.id.doctor_patient_date);
            mMainConst=itemView.findViewById(R.id.main_const);
            mSelectionLayout=itemView.findViewById(R.id.selection_layout);
            mAcceptCv=itemView.findViewById(R.id.accept_cv);
            mAcceptIv=itemView.findViewById(R.id.accept_iv);
            mRejectCv=itemView.findViewById(R.id.reject_cv);
            mRejectIv=itemView.findViewById(R.id.reject_iv);

        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
