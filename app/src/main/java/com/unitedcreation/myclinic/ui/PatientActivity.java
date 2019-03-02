package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unitedcreation.myclinic.adapter.PatientRecyclerAdapter;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getCursor;
import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;

public class PatientActivity extends AppCompatActivity {

    @BindView(R.id.mpatient_logout_button)
    ImageButton logOutButton;

    @BindView(R.id.tv_mpatient_profile)
    TextView mName;

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

        PatientRecyclerAdapter adapter = new PatientRecyclerAdapter();
        patient_rv.setAdapter(adapter);

        Cursor cursor = getCursor(this);
        /*
          Setting value to corresponding TextViews from the database.
         */
        if(cursor.moveToNext()){

            mName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));

        }
        cursor.close();

        /*
          Deleting all the user data from the database and Logging out the user on the click of logout button.
         */
        logOutButton.setOnClickListener(v -> SignOut(this));
    }
}
