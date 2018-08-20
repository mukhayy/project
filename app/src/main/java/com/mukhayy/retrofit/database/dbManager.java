package com.mukhayy.retrofit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

public class dbManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public dbManager(Context c) {
        context = c;
    }
    public dbManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String phone) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.PHONE, phone);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }


    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.FIRTSNAME, DatabaseHelper.LASTNAME, DatabaseHelper.PHONE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String firstName, String lastName, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.FIRTSNAME, firstName);
        contentValues.put(DatabaseHelper.LASTNAME, lastName);
        //contentValues.put(DatabaseHelper.PHONE, phone);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.PHONE + " = " + phone, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public Cursor getDataFromDB(String phoneNumber){
       // database = dbHelper.getWritableDatabase();
        Cursor cursor1 = database.rawQuery("Select * from " + DatabaseHelper.TABLE_NAME
                + " where " + DatabaseHelper.PHONE + " = " + phoneNumber, null);
        return cursor1;
    }

    public Boolean getUser(String phoneNumber){
        Cursor cursor = database.rawQuery("SELECT phone FROM " + DatabaseHelper.TABLE_NAME
                + " where " + DatabaseHelper.PHONE + " = " + phoneNumber, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            return true;
        }
        cursor.close();
        close();
        return false;
    }



////////////////////////////////////////////////////
   /* public Cursor getPhone(){
        Cursor cursor2 = database.rawQuery("Select phone from " + DatabaseHelper.TABLE_NAME, null);
        return cursor2;
    }
*/

}
