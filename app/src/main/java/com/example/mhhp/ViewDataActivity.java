package com.example.mhhp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewDataActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TextView dataDisplayTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        dataDisplayTextView = findViewById(R.id.dataDisplayTextView);
        backButton = findViewById(R.id.backButton);
        databaseHelper = new DatabaseHelper(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрыть активность и вернуться назад
            }
        });

        displayData();
    }

    private void displayData() {
        Cursor cursor = databaseHelper.getAllHealthData();
        if (cursor.getCount() == 0) {
            dataDisplayTextView.setText("No data available");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            stringBuilder.append("Weight: ").append(cursor.getDouble(1)).append("\n");
            stringBuilder.append("Blood Pressure: ").append(cursor.getString(2)).append("\n");
            stringBuilder.append("Pulse: ").append(cursor.getString(3)).append("\n");
            stringBuilder.append("Timestamp: ").append(cursor.getString(4)).append("\n\n");
        }
        dataDisplayTextView.setText(stringBuilder.toString());
    }
}