package com.unitedcreation.myclinic.ui.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unitedcreation.myclinic.adapter.PatientRecyclerAdapter;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.model.Bank;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.utils.FireBaseUtils;

import java.util.ArrayList;
import java.util.Objects;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getCursor;
import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;
import static com.unitedcreation.myclinic.utils.StringUtils.BANKS;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.GOVERNMENT;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;

public class PatientActivity extends AppCompatActivity {

    @BindView(R.id.mpatient_logout_button)
    ImageButton logOutButton;

    @BindView(R.id.tv_mpatient_profile)
    TextView mName;

    private String mUserName;

    private ArrayList<Doctor> doctorList = new ArrayList<>();

    private ArrayList<String> doctorKeyList = new ArrayList<>();

    @BindView(R.id.tv_mpatient_age)
    TextView mAge;

    @BindView(R.id.patient_rv)
    RecyclerView patient_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        patient_rv.setLayoutManager(layoutManager);

        Cursor cursor = getCursor(this);
        patient_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Selected"," "+patient_rv.getChildPosition(v));
            }
        });
        // Setting value to corresponding TextViews from the database.
        if(cursor.moveToNext()){

            mName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));
            mUserName=cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME));
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));

        }
        cursor.close();

        PatientRecyclerAdapter adapter = new PatientRecyclerAdapter(this,doctorList,doctorKeyList,mUserName);
        patient_rv.setAdapter(adapter);
        // Attaching a listener to read the data at returned database reference.
        new FireBaseUtils().getDatabaseReference().child(USERS).child(DOCTOR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor doctor=snapshot.getValue(Doctor.class);
                    doctorList.add(doctor);
                    doctorKeyList.add(snapshot.getKey());
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
