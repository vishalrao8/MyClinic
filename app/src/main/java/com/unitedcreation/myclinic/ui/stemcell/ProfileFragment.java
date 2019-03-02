package com.unitedcreation.myclinic.ui.stemcell;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getCursor;
import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;

public class ProfileFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mAddress = view.findViewById(R.id.tv_patient_address);
        TextView mAge = view.findViewById(R.id.tv_patient_age);
        TextView mName = view.findViewById(R.id.tv_patient_name);
        TextView mProfileName = view.findViewById(R.id.tv_patient_profile);
        TextView mState = view.findViewById(R.id.tv_patient_city);
        Button louOutButton = view.findViewById(R.id.patient_logout_button);

        Cursor cursor = getCursor(getActivity());

        if (cursor.moveToNext()) {

            mName.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)));
            mState.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_STATE)));
            mProfileName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));
            mAge.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_AGE)));
            mAddress.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_ADDRESS)));

        }
        cursor.close();

        louOutButton.setOnClickListener(v -> SignOut(getActivity()));
    }
}
