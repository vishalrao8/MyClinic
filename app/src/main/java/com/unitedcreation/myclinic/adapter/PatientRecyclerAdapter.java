package com.unitedcreation.myclinic.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.button.MaterialButton;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.model.Appointment;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.utils.FireBaseUtils;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.unitedcreation.myclinic.utils.StringUtils.APPOINTMENTS;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;

public class PatientRecyclerAdapter extends RecyclerView.Adapter <PatientRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Doctor> doctorList;
    List<String> doctorKeyList;
    String mUserName;

    public PatientRecyclerAdapter(Context context,List<Doctor> doctorList,List<String> doctorKeyList,String mUserName)
    {
        this.mUserName=mUserName;
        this.context=context;
        this.doctorList=doctorList;
        this.doctorKeyList=doctorKeyList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mDoctorName.setText("Dr. "+ doctorList.get(position).getName());
        holder.mRatingBar.setRating(Math.abs(5-position));
        holder.mPatientLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Appointment appointment=new Appointment(mUserName,String.valueOf(hourOfDay)+" : "+String.valueOf(minute));
                                new FireBaseUtils().getDatabaseReference().child(USERS).child(DOCTOR).child(doctorKeyList.get(position)).child(APPOINTMENTS).
                                        push().setValue(appointment);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mDoctorName;
        RatingBar mRatingBar;
        ConstraintLayout mPatientLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDoctorName=itemView.findViewById(R.id.patient_doctor_name);
            mRatingBar=itemView.findViewById(R.id.patient_doctor_rating);
            mPatientLayout=itemView.findViewById(R.id.patient_layout);
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }
}
