package com.unitedcreation.myclinic.ui.stemcell;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.adapter.BankRecyclerAdapter;
import com.unitedcreation.myclinic.model.Bank;
import com.unitedcreation.myclinic.utils.FireBaseUtils;
import com.unitedcreation.myclinic.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.unitedcreation.myclinic.utils.StringUtils.BANKS;
import static com.unitedcreation.myclinic.utils.StringUtils.GOVERNMENT;
import static com.unitedcreation.myclinic.utils.StringUtils.INT_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.PRIVATE;

public class BankFragment extends Fragment {

    private ArrayList<Bank> bankList = new ArrayList<>();

    private BankRecyclerAdapter bankRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank,container,false);

        RecyclerView mRecyclerView = view.findViewById(R.id.common_rv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int tabPosition = Objects.requireNonNull(getArguments()).getInt(INT_EXTRA, 0);

        // Attaching a listener to read the data at returned database reference.
        Objects.requireNonNull(getReference(tabPosition)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                
                    Bank bank = snapshot.getValue(Bank.class);
                    bankList.add(bank);

                }

                bankRecyclerAdapter = new BankRecyclerAdapter(bankList);
                mRecyclerView.setAdapter(bankRecyclerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });
        return view;

    }

    /**
     * Returning list of bank based on tab position.
     * 0 for government.
     * 1 for private.
     * @param tabPosition identifier for category.
     * @return Database reference at appropriate root according to identifier.
     */
    private DatabaseReference getReference (int tabPosition) {

        switch (tabPosition) {
            case 0:
                return new FireBaseUtils().getDatabaseReference().child(BANKS).child(GOVERNMENT);

            case 1:
                return new FireBaseUtils().getDatabaseReference().child(BANKS).child(PRIVATE);

            default:
                return null;
        }
    }
}
