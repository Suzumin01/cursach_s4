package com.example.mhhp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.database.Cursor;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private TextView greetingTextView, averageWeightTextView, averageBloodPressureTextView, averagePulseTextView;
    private LineChart weightChart;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        greetingTextView = view.findViewById(R.id.greetingTextView);
        averageWeightTextView = view.findViewById(R.id.averageWeightTextView);
        averageBloodPressureTextView = view.findViewById(R.id.averageBloodPressureTextView);
        averagePulseTextView = view.findViewById(R.id.averagePulseTextView);

        weightChart = view.findViewById(R.id.weightChart);

        databaseHelper = new DatabaseHelper(getActivity());

        setupWeightChart();

        // Загрузка имени и отчества пользователя из SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
        String firstName = preferences.getString("firstName", "User");
        String middleName = preferences.getString("middleName", "");
        greetingTextView.setText(firstName + " " + middleName + ", here are your health parameters");

        // Вычисление и отображение средних показателей
        double averageWeight = databaseHelper.getAverageWeight();
        String averageBloodPressure = databaseHelper.getAverageBloodPressure();
        double averagePulse = databaseHelper.getAveragePulse();

        averageWeightTextView.setText("Average Weight: " + averageWeight);
        averageBloodPressureTextView.setText("Average Blood Pressure: " + averageBloodPressure);
        averagePulseTextView.setText("Average Pulse: " + averagePulse);

        return view;
    }
    private void setupWeightChart() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>(); // Список для хранения дат

        Cursor cursor = databaseHelper.getAllHealthData();
        int weightColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_WEIGHT);
        int timestampColumnIndex = cursor.getColumnIndex(databaseHelper.getTimestampColumnName());

        // Проверяем наличие нужных столбцов
        if (weightColumnIndex != -1 && timestampColumnIndex != -1 && cursor.moveToFirst()) {
            do {
                float weight = cursor.getFloat(weightColumnIndex);
                String timestamp = cursor.getString(timestampColumnIndex); // Получаем дату из базы данных
                entries.add(new Entry(cursor.getPosition(), weight));
                dates.add(timestamp); // Добавляем дату в список
            } while (cursor.moveToNext());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Weight");
        LineData lineData = new LineData(dataSet);
        weightChart.setData(lineData);

        Description description = new Description();
        description.setText("");
        weightChart.setDescription(description);

        // Настройка оси X с датами
        XAxis xAxis = weightChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return ""; // Скрытие меток на оси X
            }
        });
        xAxis.setGranularity(1f); // Установка интервала между метками

        weightChart.invalidate(); // refresh
    }

}

