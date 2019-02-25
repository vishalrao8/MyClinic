package com.unitedcreation.myclinic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataTableHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="smflexi.db";
    private static final int DATABASE_VERSION=1;

    //Constructor for DataListDBHelper
    public DataTableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE "+ DataContract.DataTable.TABLE_NAME+"("+DataContract.DataTable.P_ID+" INTEGER PRIMARY KEY ,"+
                DataContract.DataTable.P_NAME +" TEXT NOT NULL ,"+
                DataContract.DataTable.P_AGE +" INTEGER NOT NULL ,"+
                DataContract.DataTable.P_STATE +" TEXT NOT NULL ,"+
                DataContract.DataTable.P_ADDRESS +" TEXT NOT NULL ,"+
                DataContract.DataTable.P_ISSUE +" TEXT ,"+
                DataContract.DataTable.P_QUALIFICATION +" TEXT ,"+
                DataContract.DataTable.P_LICENCE +" TEXT"+")";
        db.execSQL(create);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.DataTable.TABLE_NAME);
        onCreate(db);

    }
    public boolean insertItem(String name,int id,int age,String address,String state,String issue,String qualification,String licence) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.DataTable.P_NAME,name);
        contentValues.put(DataContract.DataTable.P_ID,id);
        contentValues.put(DataContract.DataTable.P_AGE,age);
        contentValues.put(DataContract.DataTable.P_ADDRESS,address);
        contentValues.put(DataContract.DataTable.P_STATE,state);
        contentValues.put(DataContract.DataTable.P_ISSUE,issue);
        contentValues.put(DataContract.DataTable.P_QUALIFICATION,qualification);
        contentValues.put(DataContract.DataTable.P_LICENCE,licence);
        long result = db.insert(DataContract.DataTable.TABLE_NAME,null ,contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ DataContract.DataTable.TABLE_NAME,null);
        return res;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DataContract.DataTable.TABLE_NAME);
        db.close();
    }


}
