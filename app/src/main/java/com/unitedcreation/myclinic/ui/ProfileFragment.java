package com.unitedcreation.myclinic.ui;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.database.DataTableHelper;
import com.unitedcreation.myclinic.utils.StringUtils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.unitedcreation.myclinic.utils.ViewUtils.moveToHome;

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

    @SuppressLint("ApplySharedPref")
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
            mProfileName.setText("Hi " + cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)) + ",");
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));
            mAddress.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_ADDRESS)));

        }
        mLogout.setOnClickListener(v -> {

           try
           {
               FirebaseAuth.getInstance().signOut();
               FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                   @Override
                   public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                       firebaseAuth.getUid();
                   }
               });
               Log.i("LOGOUT","LOGOUT");
               dataTableHelper.deleteData();
               getActivity().getSharedPreferences(StringUtils.USER_ID, Context.MODE_PRIVATE).edit().putString(StringUtils.USER_ID,null).commit();
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }

            moveToHome(getActivity());
            Objects.requireNonNull(getActivity()).finish();
        });
    }
}
