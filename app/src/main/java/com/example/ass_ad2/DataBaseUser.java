package com.example.ass_ad2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseUser extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "userData";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public DataBaseUser(@Nullable Context context) {
        super(context, "user.sqlite", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean InsertData(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long newRowID = db.insert(TABLE_NAME, null, values);

        if (newRowID == -1) return false;
        else return true;
    }

    public Boolean checkUser(String username) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " =? ", new String[]{
                username
        });
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUserPass(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " =? AND " + COLUMN_PASSWORD + " =?",
                new String[]{username, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
