package com.unitedcreation.myclinic.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.takisoft.datetimepicker.TimePickerDialog;
import com.takisoft.datetimepicker.widget.DatePicker;
import com.takisoft.datetimepicker.widget.TimePicker;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.model.Appointment;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.utils.FireBaseUtils;
import com.unitedcreation.myclinic.utils.PreferencesUtils;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.unitedcreation.myclinic.utils.StringUtils.APPOINTMENTS;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.PENDING;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;

public class PatientRecyclerAdapter extends RecyclerView.Adapter <PatientRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Doctor> doctorList;
    private List<String> doctorKeyList;
    private String mUserName;
    String time="",date="";

    public PatientRecyclerAdapter(Context context,List<Doctor> doctorList,List<String> doctorKeyList,String mUserName) {

        this.mUserName = mUserName;
        this.context = context;
        this.doctorList = doctorList;
        this.doctorKeyList = doctorKeyList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mDoctorName.setText(String.format("Dr. %s", doctorList.get(position).getName()));
        holder.mRatingBar.setRating(Math.abs(5-position));
        holder.mPatientLayout.setOnClickListener(v -> {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            DatePickerDialog datePickerDialog=new DatePickerDialog(context);
            datePickerDialog.show();

            TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                    Appointment appointment = new Appointment(mUserName,doctorList.get(position).getName(),time,date,PENDING,doctorKeyList.get(position),PreferencesUtils.getUserId(context));
                    new FireBaseUtils().getDatabaseReference().child(APPOINTMENTS).
                            push().setValue(appointment);
                }
            },mHour,mMinute,true);
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date=String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                    timePickerDialog.show();
                }
            });

        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mDoctorName;
        RatingBar mRatingBar;
        ConstraintLayout mPatientLayout;

        ViewHolder(@NonNull View itemView) {
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
