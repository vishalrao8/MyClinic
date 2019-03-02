package com.unitedcreation.myclinic.utils;

import android.content.Context;
import android.database.Cursor;

import com.unitedcreation.myclinic.database.DataTableHelper;

public class DatabaseUtils {

    private static DataTableHelper sInstance;

    public static DataTableHelper getDataTableHelper (Context context) {

        if (sInstance == null) {
            synchronized (new Object()) {
                sInstance = new DataTableHelper(context);
            }
        }
        return sInstance;
    }

    public static Cursor getCursor (Context context) {
        return getDataTableHelper(context).getAllData();
    }

    static void deleteAllData(Context context) {
        getDataTableHelper(context).deleteData();
    }
}
