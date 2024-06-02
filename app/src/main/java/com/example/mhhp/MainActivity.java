package com.example.mhhp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity {

    private EditText weightInput, heightInput, bloodPressureInput, pulseInput;
    private Button saveButton, viewButton;
    private HealthData healthData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        bloodPressureInput = findViewById(R.id.bloodPressureInput);
        pulseInput = findViewById(R.id.pulseInput);
        saveButton = findViewById(R.id.saveButton);
        viewButton = findViewById(R.id.viewButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHealthData();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHealthData();
            }
        });
        setDailyReminder(this);
    }

    public static void setDailyReminder(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void saveHealthData() {
        float weight = Float.parseFloat(weightInput.getText().toString());
        float height = Float.parseFloat(heightInput.getText().toString());
        String bloodPressure = bloodPressureInput.getText().toString();
        int pulse = Integer.parseInt(pulseInput.getText().toString());

        healthData = new HealthData(weight, height, bloodPressure, pulse);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("weight", weight);
        editor.putFloat("height", height);
        editor.putString("bloodPressure", bloodPressure);
        editor.putInt("pulse", pulse);
        editor.apply();

        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    private void viewHealthData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        float weight = preferences.getFloat("weight", 0);
        float height = preferences.getFloat("height", 0);
        String bloodPressure = preferences.getString("bloodPressure", "N/A");
        int pulse = preferences.getInt("pulse", 0);

        String data = "Weight: " + weight + " kg\n" +
                "Height: " + height + " cm\n" +
                "Blood Pressure: " + bloodPressure + " mmHg\n" +
                "Pulse: " + pulse + " bpm";
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}