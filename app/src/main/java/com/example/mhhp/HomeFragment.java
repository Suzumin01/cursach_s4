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

public class HomeFragment extends Fragment {

    private TextView greetingTextView, averageWeightTextView, averageBloodPressureTextView, averagePulseTextView;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        greetingTextView = view.findViewById(R.id.greetingTextView);
        averageWeightTextView = view.findViewById(R.id.averageWeightTextView);
        averageBloodPressureTextView = view.findViewById(R.id.averageBloodPressureTextView);
        averagePulseTextView = view.findViewById(R.id.averagePulseTextView);
        databaseHelper = new DatabaseHelper(getActivity());

        // Загрузка имени и отчества пользователя из SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
        String firstName = preferences.getString("firstName", "User");
        String middleName = preferences.getString("middleName", "");
        greetingTextView.setText("Здравствуйте, " + firstName + " " + middleName + "!");

        // Вычисление и отображение средних показателей
        double averageWeight = databaseHelper.getAverageWeight();
        String averageBloodPressure = databaseHelper.getAverageBloodPressure();
        double averagePulse = databaseHelper.getAveragePulse();

        averageWeightTextView.setText("Average Weight: " + averageWeight);
        averageBloodPressureTextView.setText("Average Blood Pressure: " + averageBloodPressure);
        averagePulseTextView.setText("Average Pulse: " + averagePulse);

        return view;
    }
}

