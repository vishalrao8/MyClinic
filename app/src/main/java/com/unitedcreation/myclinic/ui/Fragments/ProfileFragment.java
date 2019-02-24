package com.unitedcreation.myclinic.ui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.SQLiteDatabase.DataTableHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView mName,mProfileName,mAge,mAddress,mState,mVARName,mVARValue,mLogout;
    DataTableHelper dataTableHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataTableHelper=new DataTableHelper(getContext());
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);/*
        mAddress=view.findViewById(R.id.profile_address);
        mAge=view.findViewById(R.id.profile_age);
        mName=view.findViewById(R.id.profile_name);
        mProfileName=view.findViewById(R.id.tv_patient_profile);
        mState=view.findViewById(R.id.profile_state);
        mVARName=view.findViewById(R.id.profile_var_name);
        mLogout=view.findViewById(R.id.tv_profile_logout);
        mVARValue=view.findViewById(R.id.profile_var_value);

        Cursor cursor=dataTableHelper.getAllData();
        if(cursor.moveToNext()){
            mName.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)));
            mState.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_STATE)));
            mProfileName.setText("Hi ,"+cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)));
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));
            mAddress.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_ADDRESS)));
            if(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_ISSUE))!=null){
                mVARValue.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_ISSUE)));
                mVARName.setText("Issue : ");
            }
            else if(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE))!=null){
                mVARValue.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE)));
                mVARName.setText("Licence : ");
            }

        }
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FirebaseAuth.getInstance().signOut();
                dataTableHelper.deleteData();
                Log.i("LOGOUT","LOGOUT");
                getActivity().finishAndRemoveTask();
            }
        });*/
    }
}
