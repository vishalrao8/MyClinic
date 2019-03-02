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
import com.unitedcreation.myclinic.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.unitedcreation.myclinic.utils.StringUtils.INT_EXTRA;

public class BankFragment extends Fragment {

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    List<Bank> bankList=null;
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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        /*
          Returning list of bank based on tab position.
          0 for government.
          1 for private.
         */
        switch (tabPosition) {

            case 0:
                ref = database.getReference("/"+ StringUtils.BANKS+"/"+StringUtils.GOVERNMENT);
                break;

            case 1:
                ref = database.getReference("/"+ StringUtils.BANKS+"/"+StringUtils.PRIVATE);

        }

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bankList=new ArrayList<Bank>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Bank bank = dataSnapshot1.getValue(Bank.class);
                    Log.d("BANK",bank.getName());
                    bankList.add(bank);
                }
                bankRecyclerAdapter=new BankRecyclerAdapter(bankList);
                mRecyclerView.setAdapter(bankRecyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DATA",databaseError.getDetails());
            }
        });
        return view;
    }
}
