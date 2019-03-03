package com.unitedcreation.myclinic.ui.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unitedcreation.myclinic.adapter.DoctorRecyclerAdapter;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.model.Appointment;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.utils.FireBaseUtils;

import java.util.ArrayList;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getCursor;
import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.getUserId;
import static com.unitedcreation.myclinic.utils.StringUtils.APPOINTMENTS;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;

public class DoctorActivity extends AppCompatActivity {

    private ArrayList<Appointment> appointments = new ArrayList<>();
    private String uid;

    @BindView(R.id.doctor_logout_button)
    ImageButton logOutButton;

    @BindView(R.id.doctor_rv)
    RecyclerView doctorRecyclerView;

    @BindView(R.id.tv_doctor_profile)
    TextView doctorName;

    @BindView(R.id.tv_doctor_licence)
    TextView doctorLicence;

    @BindView(R.id.tv_doctor_qualification)
    TextView doctorQualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        uid = getUserId(this);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        doctorRecyclerView.setLayoutManager(layoutManager);

        DoctorRecyclerAdapter adapter = new DoctorRecyclerAdapter(appointments);
        doctorRecyclerView.setAdapter(adapter);

        Cursor cursor = getCursor(this);

        // Setting value to corresponding TextViews from the database.
        if (cursor.moveToNext()) {

            doctorQualification.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_QUALIFICATION)));
            doctorLicence.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE)));
            doctorName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));

        }
        cursor.close();

        // Attaching a listener to read the data at returned database reference.
        new FireBaseUtils().getDatabaseReference().child(USERS).child(DOCTOR).child(uid).child(APPOINTMENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Appointment appointment = snapshot.getValue(Appointment.class);
                    appointments.add(appointment);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });

            // Deleting all the user data from the database and Logging out the user on the click of logout button.
        logOutButton.setOnClickListener(v -> SignOut(this));
    }
}
