package com.unitedcreation.myclinic.ui;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.ui.Fragments.BankFragment;
import com.unitedcreation.myclinic.ui.Fragments.ProfileFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.widget.TextView;

import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class StemActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_stem);

        // load the store fragment by default
        loadFragment(new BankFragment());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                Fragment fragment=null;

                switch (item.getItemId()) {

                    case R.id.navigation_bank: fragment = new BankFragment(); break;

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
}
