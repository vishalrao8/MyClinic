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
import com.unitedcreation.myclinic.model.Appointment;
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

public class PatientAppointmentListFragment extends Fragment implements DoctorRecyclerAdapter.onButtonClick {
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private ArrayList<String> appointmentskeylist = new ArrayList<>();

    public PatientAppointmentListFragment(){}

    public PatientAppointmentListFragment(Context context)
    {
        this.context=context;

    }

    RecyclerView doctorRecyclerView;
    ApprovedAppointmentRecyclerAdapter adapter;
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

        adapter = new ApprovedAppointmentRecyclerAdapter(appointments,appointmentskeylist);
        doctorRecyclerView.setAdapter(adapter);

        new FireBaseUtils().getDatabaseReference().child(APPOINTMENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if(appointment.getPatientId().equals(PreferencesUtils.getUserId(context)))
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

    @Override
    public void onClicked() {
        new FireBaseUtils().getDatabaseReference().child(APPOINTMENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                appointmentskeylist.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if(appointment.getPatientId().equals(PreferencesUtils.getUserId(context)))
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
