package com.unitedcreation.myclinic.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.adapter.BankRecyclerAdapter;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.unitedcreation.myclinic.utils.StringUtils.INT_EXTRA;

public class BankFragment extends Fragment {

    private String gov_hospital_names[] = {"Jeevan", "STM", "Cryosave", "Stem cyte india"};
    private String gov_hospital_lat[] = {"13.116800", "28.632430", "28.632430", "12.930584"};
    private String gov_hospital_long[] = {"80.275833", "77.218790", "77.218790", "77.692057"};
    private String private_hospital_names[] = {"LifeCell", "Cryo-Cell International", "Relicord", "Lifecell Babycord Stem Cells Bank", "ReeLabs"};
    private String private_hospital_lat[] = {"12.937658", "19.40728", "43.002701", "36.1999", "72.833291"};
    private String private_hospital_long[] = {"77.627754", "-99.271225", "-82.802469", "-78.7222", "18.936011"};

    private BankRecyclerAdapter bankRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank,container,false);
        RecyclerView mRecyclerView = view.findViewById(R.id.common_rv);

        int tabPosition = Objects.requireNonNull(getArguments()).getInt(INT_EXTRA, 0);

        /*
          Returning list of bank based on tab position.
          0 for government.
          1 for private.
         */
        switch (tabPosition) {

            case 0:
                bankRecyclerAdapter = new BankRecyclerAdapter(gov_hospital_names,
                        gov_hospital_lat,
                        gov_hospital_long);
                break;

            case 1:
                bankRecyclerAdapter = new BankRecyclerAdapter(private_hospital_names,
                        private_hospital_lat,
                        private_hospital_long);

        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(bankRecyclerAdapter);

        return view;
    }
}
