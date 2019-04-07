package com.unitedcreation.myclinic.ui.patient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.adapter.ApprovedAppointmentRecyclerAdapter;
import com.unitedcreation.myclinic.adapter.DoctorRecyclerAdapter;
import com.unitedcreation.myclinic.adapter.PatientRecyclerAdapter;
import com.unitedcreation.myclinic.model.Appointment;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.utils.FireBaseUtils;
import com.unitedcreation.myclinic.utils.PreferencesUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.unitedcreation.myclinic.utils.StringUtils.APPOINTMENTS;
import static com.unitedcreation.myclinic.utils.StringUtils.APPROVED;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;

public class DoctorListFragment extends Fragment implements DoctorRecyclerAdapter.onButtonClick {
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private ArrayList<String> appointmentskeylist = new ArrayList<>();

    private String mUserName;

    private ArrayList<Doctor> doctorList = new ArrayList<>();

    private ArrayList<String> doctorUid = new ArrayList<>();

    public DoctorListFragment(Context context)
    {
        this.context=context;

    }
    public DoctorListFragment(){}
    RecyclerView doctorRecyclerView;
    PatientRecyclerAdapter  adapter;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_appointment,container,false);
        doctorRecyclerView=view.findViewById(R.id.fragment_appointment_rv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        doctorRecyclerView.setLayoutManager(layoutManager);

        adapter = new PatientRecyclerAdapter(context,doctorList, doctorUid,mUserName);
        doctorRecyclerView.setAdapter(adapter);
        mUserName=PreferencesUtils.getPatientData(context).getName();

        new FireBaseUtils().getDatabaseReference().child(USERS).child(DOCTOR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Doctor doctor = snapshot.getValue(Doctor.class);
                    doctorList.add(doctor);
                    doctorUid.add(snapshot.getKey());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });
    }

    @Override
    public void onClicked() {
        new FireBaseUtils().getDatabaseReference().child(APPOINTMENTS).child(APPROVED).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                appointmentskeylist.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if(appointment.getDoctorId().equals(PreferencesUtils.getUserId(context)))
                    {
                        appointments.add(appointment);
                        appointmentskeylist.add(snapshot.getKey());
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });
    }
}
