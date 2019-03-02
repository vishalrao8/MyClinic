package com.unitedcreation.myclinic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataTableHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smflexi.db";
    private static final int DATABASE_VERSION = 1;

    public DataTableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method executing SQLiteDatabase instance.
     * @param db SQLiteDatabase instance to be created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE " + DataContract.DataTable.TABLE_NAME + "(" +
                DataContract.DataTable.P_ID + " INTEGER PRIMARY KEY ," +
                DataContract.DataTable.P_NAME + " TEXT NOT NULL ," +
                DataContract.DataTable.P_AGE + " INTEGER NOT NULL ," +
                DataContract.DataTable.P_STATE + " TEXT NOT NULL ," +
                DataContract.DataTable.P_ADDRESS + " TEXT NOT NULL ," +
                DataContract.DataTable.P_ISSUE + " TEXT ," +
                DataContract.DataTable.P_QUALIFICATION + " TEXT ," +
                DataContract.DataTable.P_LICENCE + " TEXT" + ")";

        db.execSQL(create);

    }

    /**
     * Utility method to get data upgraded in the database.
     * @param db Database instance.
     * @param oldVersion Numerical value holding old version of database.
     * @param newVersion Numerical value holding new version of database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DataContract.DataTable.TABLE_NAME);
        onCreate(db);

    }

    /**
     * Utility method to insert data in the database.
     * @param name Name of user.
     * @param id Primary Key.
     * @param age Age of user.
     * @param address Address of user.
     * @param state State of user.
     * @param issue issue of user.
     * @param qualification Qualification of user.
     * @param licence Licence number of user.
     * @return A boolean value indicating successful insertion of data into the database.
     */
    public boolean insertItem(String name, int id, int age, String address, String state, String issue, String qualification, String licence) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.DataTable.P_NAME, name);
        contentValues.put(DataContract.DataTable.P_ID, id);
        contentValues.put(DataContract.DataTable.P_AGE, age);
        contentValues.put(DataContract.DataTable.P_ADDRESS, address);
        contentValues.put(DataContract.DataTable.P_STATE, state);
        contentValues.put(DataContract.DataTable.P_ISSUE, issue);
        contentValues.put(DataContract.DataTable.P_QUALIFICATION, qualification);
        contentValues.put(DataContract.DataTable.P_LICENCE, licence);

        long result = db.insert(DataContract.DataTable.TABLE_NAME, null, contentValues);

        db.close();

        return result != -1;

    }

    /**
     * Utility method to fetch all the data from the database.
     * @return Cursor at the beginning of the database capable of moving to the next position.
     */
    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("select * from " + DataContract.DataTable.TABLE_NAME, null);
        return cursor;
    }

    /**
     * Utility method to delete all the data from the database.
     */
    public void deleteData() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DataContract.DataTable.TABLE_NAME);
        db.close();
    }
}
