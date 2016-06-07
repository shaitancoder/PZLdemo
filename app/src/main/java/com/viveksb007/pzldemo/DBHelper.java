package com.viveksb007.pzldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by viveksb007 on 6/7/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QRCODE";
    public static final String QR_CODE_HISTORY_TABLE = "qrCodeHistory";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + QR_CODE_HISTORY_TABLE + " (id INTEGER PRIMARY KEY, qrcode TEXT, created_at DATETIME DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QR_CODE_HISTORY_TABLE);
        onCreate(db);
    }

    public boolean insertCode(String qrcode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("qrcode", qrcode);
        db.insert(QR_CODE_HISTORY_TABLE, null, contentValues);
        return true;
    }

    public ArrayList<String> getAllHistory(){
        ArrayList<String> qrcodeHistory = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QR_CODE_HISTORY_TABLE, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            qrcodeHistory.add(cursor.getString(cursor.getColumnIndex("qrcode")));
            cursor.moveToNext();
        }
        return qrcodeHistory;
    }
}
