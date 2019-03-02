package com.unitedcreation.myclinic.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unitedcreation.myclinic.database.DataTableHelper;

import androidx.annotation.NonNull;

import static com.unitedcreation.myclinic.utils.PreferencesUtils.removeUser;
import static com.unitedcreation.myclinic.utils.ViewUtils.moveToHome;

public class FireBaseUtils {

    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static void SignOut (DataTableHelper dataTableHelper, Context context) {

        try {

            FirebaseAuth.getInstance().signOut();
            FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    firebaseAuth.removeAuthStateListener(this);
                    dataTableHelper.deleteData();
                    removeUser(context);
                    moveToHome(context);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
