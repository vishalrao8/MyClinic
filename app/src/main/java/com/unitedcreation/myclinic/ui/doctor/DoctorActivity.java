package com.unitedcreation.myclinic.ui.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getCursor;
import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;

public class DoctorActivity extends AppCompatActivity {

    @BindView(R.id.doctor_logout_button)
    ImageButton logOutButton;

    private String tabTitles[] = {"Appointments", "Approved"};

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
        ButterKnife.bind(this);

        TabLayout tabLayout = findViewById(R.id.tabs_doctor);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_government)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_private)));

        final ViewPager viewPager = findViewById(R.id.viewpager_doctor);
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
        if (cursor.moveToNext()) {

            doctorQualification.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_QUALIFICATION)));
            doctorLicence.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE)));
            doctorName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));

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

                case 0: fragment=new AppointmentListFragment(DoctorActivity.this);
                    break;

                case 1: fragment=new ApprovedAppointmentListFragment(DoctorActivity.this);

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
