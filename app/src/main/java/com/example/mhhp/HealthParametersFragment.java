package com.example.mhhp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HealthParametersFragment extends Fragment {

    private EditText weightInput, bloodPressureInput, pulseInput;
    private Button saveButton, clearDataButton;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_parameters, container, false);

        weightInput = view.findViewById(R.id.weightInput);
        bloodPressureInput = view.findViewById(R.id.bloodPressureInput);
        pulseInput = view.findViewById(R.id.pulseInput);
        saveButton = view.findViewById(R.id.saveButton);
        clearDataButton = view.findViewById(R.id.clearDataButton);

        databaseHelper = new DatabaseHelper(getActivity());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHealthData();
            }
        });

        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHealthData();
            }
        });

        return view;
    }

    private void saveHealthData() {
        String weightStr = weightInput.getText().toString();
        String bloodPressureStr = bloodPressureInput.getText().toString();
        String pulseStr = pulseInput.getText().toString();

        if (weightStr.isEmpty() || bloodPressureStr.isEmpty() || pulseStr.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter all parameters", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);

            // Проверка корректности формата давления
            String[] bloodPressureParts = bloodPressureStr.split("/");
            if (bloodPressureParts.length != 2) {
                throw new NumberFormatException("Invalid blood pressure format");
            }
            int systolic = Integer.parseInt(bloodPressureParts[0]);
            int diastolic = Integer.parseInt(bloodPressureParts[1]);
            String bloodPressure = systolic + "/" + diastolic;

            // Проверка корректности формата пульса
            int pulse = Integer.parseInt(pulseStr);

            boolean isInserted = databaseHelper.addHealthData(weight, bloodPressure, String.valueOf(pulse));
            if (isInserted) {
                Toast.makeText(getActivity(), "Health data saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Failed to save health data", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }




    private void clearHealthData() {
        databaseHelper.clearDatabase();
        Toast.makeText(getActivity(), "All health data cleared", Toast.LENGTH_SHORT).show();
    }
}
