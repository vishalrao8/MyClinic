package com.unitedcreation.myclinic.ui.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.SQLiteDatabase.DataContract;
import com.unitedcreation.myclinic.SQLiteDatabase.DataTableHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView mName,mProfileName,mAge,mAddress,mState;
    Button mLogout;
    DataTableHelper dataTableHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataTableHelper=new DataTableHelper(getContext());
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddress=view.findViewById(R.id.tv_patient_address);
        mAge=view.findViewById(R.id.tv_patient_age);
        mName=view.findViewById(R.id.tv_patient_name);
        mProfileName=view.findViewById(R.id.tv_patient_profile);
        mState=view.findViewById(R.id.tv_patient_city);
        mLogout=view.findViewById(R.id.patient_logout_button);

        Cursor cursor=dataTableHelper.getAllData();
        if(cursor.moveToNext()){
            mName.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)));
            mState.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_STATE)));
            mProfileName.setText("Hi ,"+cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)));
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));
            mAddress.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_ADDRESS)));

        }
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FirebaseAuth.getInstance().signOut();
                dataTableHelper.deleteData();
                Log.i("LOGOUT","LOGOUT");
                getActivity().finishAndRemoveTask();
            }
        });
    }
}
