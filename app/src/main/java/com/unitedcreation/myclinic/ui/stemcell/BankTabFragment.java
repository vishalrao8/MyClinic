package com.unitedcreation.myclinic.ui.stemcell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.unitedcreation.myclinic.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.unitedcreation.myclinic.utils.StringUtils.INT_EXTRA;

public class BankTabFragment extends Fragment {

    private Bundle bundle;
    private String tabTitles[] = {"Government", "Private"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank_tabs, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabs_patient_bank);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_government)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_private)));

        final ViewPager viewPager = view.findViewById(R.id.viewpager_patient_bank);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager()));
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

        return view;
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        int tabSize;

        PagerAdapter(FragmentManager fm) {

            super(fm);
            this.tabSize = 2;

        }

        @Override
        public Fragment getItem(int position) {
            bundle = new Bundle();
            switch (position) {

                case 0: bundle.putInt(INT_EXTRA, 0);
                        break;

                case 1: bundle.putInt(INT_EXTRA, 1);

            }

            BankFragment bankFragment = new BankFragment();
            bankFragment.setArguments(bundle);
            return bankFragment;

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
