package com.unitedcreation.myclinic.ui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.utils.StringUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BankFragment extends Fragment {

    private String tabTitles[] = {"Government", "Private"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabs_patient_bank);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_government)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.patient_tab_private)));

        final ViewPager viewPager = view.findViewById(R.id.viewpager_patient_bank);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
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

        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0: return new CommonFragment(StringUtils.GOVERNMENT);

                case 1: return new CommonFragment(StringUtils.PRIVATE);

                default: return null;

            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
        // overriding getPageTitle()

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
