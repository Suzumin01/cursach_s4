package com.example.mhhp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "health_data.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "health_data";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_BLOOD_PRESSURE = "blood_pressure";
    public static final String COLUMN_PULSE = "pulse";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHT + " REAL, " +
                COLUMN_BLOOD_PRESSURE + " TEXT, " +
                COLUMN_PULSE + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addHealthData(double weight, String bloodPressure, String pulse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_BLOOD_PRESSURE, bloodPressure);
        values.put(COLUMN_PULSE, pulse);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }
    public double getAverageWeight() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_WEIGHT + ") FROM " + TABLE_NAME, null);
        double averageWeight = 0;
        if (cursor.moveToFirst()) {
            averageWeight = cursor.getDouble(0);
        }
        cursor.close();
        return Math.round(averageWeight * 10.0) / 10.0;
    }

    public String getAverageBloodPressure() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_BLOOD_PRESSURE + " FROM " + TABLE_NAME, null);

        int systolicSum = 0;
        int diastolicSum = 0;
        int count = 0;

        while (cursor.moveToNext()) {
            String[] parts = cursor.getString(0).split("/");
            if (parts.length == 2) {
                int systolic = Integer.parseInt(parts[0]);
                int diastolic = Integer.parseInt(parts[1]);
                systolicSum += systolic;
                diastolicSum += diastolic;
                count++;
            }
        }
        cursor.close();

        if (count == 0) return "No Data";

        int averageSystolic = Math.round(systolicSum / (float) count);
        int averageDiastolic = Math.round(diastolicSum / (float) count);

        return averageSystolic + "/" + averageDiastolic;
    }

    public double getAveragePulse() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_PULSE + ") FROM " + TABLE_NAME, null);
        double averagePulse = 0;
        if (cursor.moveToFirst()) {
            averagePulse = cursor.getDouble(0);
        }
        cursor.close();
        // Округляем до одного знака после запятой
        return Math.round(averagePulse * 10.0) / 10.0;
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }


}
