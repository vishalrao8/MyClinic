package com.unitedcreation.myclinic.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.deleteAllData;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.removeUser;
import static com.unitedcreation.myclinic.utils.ViewUtils.moveToHome;

public class FireBaseUtils {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static void SignOut (Context context) {


        try {

            FirebaseAuth.getInstance().signOut();
            FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    firebaseAuth.removeAuthStateListener(this);
                    deleteAllData(context);
                    removeUser(context);
                    moveToHome(context);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
