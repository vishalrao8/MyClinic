package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unitedcreation.myclinic.Adapters.PatientRecyclerAdapter;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.SQLiteDatabase.DataContract;
import com.unitedcreation.myclinic.SQLiteDatabase.DataTableHelper;

import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class PatientActivity extends AppCompatActivity {
    @BindView(R.id.mpatient_logout_button)
    ImageButton mLogout;

    @BindView(R.id.tv_mpatient_profile)
    TextView mName;

    @BindView(R.id.tv_mpatient_age)
    TextView mAge;

    @BindView(R.id.patient_rv)
    RecyclerView patient_rv;
    DataTableHelper dataTableHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataTableHelper=new DataTableHelper(this);
        switchTheme(this, 2);
        setContentView(R.layout.activity_patient);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        patient_rv.setLayoutManager(layoutManager);

        PatientRecyclerAdapter adapter=new PatientRecyclerAdapter();
        patient_rv.setAdapter(adapter);
        Cursor cursor=dataTableHelper.getAllData();
        if(cursor.moveToNext()){
            mName.setText("Hi ,"+cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)));
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));

        }


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FirebaseAuth.getInstance().signOut();
                dataTableHelper.deleteData();
                Log.i("LOGOUT","LOGOUT");
                finishAndRemoveTask();
            }
        });
    }
}
