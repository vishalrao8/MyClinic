package com.unitedcreation.myclinic.ui.stemcell;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unitedcreation.myclinic.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class StemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stem);

        // load the store fragment by default
        loadFragment(new BankTabFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                Fragment fragment=null;

                switch (item.getItemId()) {

                    case R.id.navigation_bank: fragment = new BankTabFragment(); break;

                    case R.id.navigation_profile: fragment = new ProfileFragment();

                }

                if(fragment!=null){

                    loadFragment(fragment);
                    return true;

                }
                return false;
            };


    private void loadFragment(Fragment fragment) {

        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }
}
