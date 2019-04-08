package com.unitedcreation.myclinic.ui.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unitedcreation.myclinic.adapter.PatientRecyclerAdapter;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.ui.doctor.AppointmentListFragment;
import com.unitedcreation.myclinic.ui.doctor.ApprovedAppointmentListFragment;
import com.unitedcreation.myclinic.ui.doctor.DoctorActivity;
import com.unitedcreation.myclinic.utils.FireBaseUtils;

import java.util.ArrayList;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getCursor;
import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;

public class PatientActivity extends AppCompatActivity {

    @BindView(R.id.mpatient_logout_button)
    ImageButton logOutButton;

    private String tabTitles[] = {"Doctors", "Appointment"};

    @BindView(R.id.tv_mpatient_profile)
    TextView mName;

    @BindView(R.id.tv_mpatient_age)
    TextView mAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        ButterKnife.bind(this);

        TabLayout tabLayout = findViewById(R.id.tabs_patient);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_government)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_private)));

        final ViewPager viewPager = findViewById(R.id.viewpager_patient);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Cursor cursor = getCursor(this);
        // Setting value to corresponding TextViews from the database.
        if(cursor.moveToNext()){

            mName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));

        }
        cursor.close();




        // Attaching a listener to read the data at returned database reference.


        // Deleting all the user data from the database and Logging out the user on the click of logout button.
        logOutButton.setOnClickListener(v -> SignOut(this));
    }
    public class PagerAdapter extends FragmentStatePagerAdapter {

        int tabSize;

        PagerAdapter(FragmentManager fm) {

            super(fm);
            this.tabSize = 2;

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position) {

                case 0: fragment=new DoctorListFragment(PatientActivity.this);
                    break;

                case 1: fragment=new PatientAppointmentListFragment(PatientActivity.this);

            }
            return fragment;

        }

        @Override
        public int getCount() {
            return tabSize;
        }
        // overriding getPageTitle()

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
