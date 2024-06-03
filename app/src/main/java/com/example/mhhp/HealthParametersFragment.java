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

    private EditText weightInput, heightInput, bloodPressureInput, pulseInput;
    private Button saveButton, clearDataButton;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_parameters, container, false);

        weightInput = view.findViewById(R.id.weightInput);
        heightInput = view.findViewById(R.id.heightInput);
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
        double weight = Double.parseDouble(weightInput.getText().toString());
        double height = Double.parseDouble(heightInput.getText().toString());
        String bloodPressure = bloodPressureInput.getText().toString();
        String pulse = pulseInput.getText().toString();

        boolean isInserted = databaseHelper.addHealthData(weight, height, bloodPressure, pulse);
        if (isInserted) {
            Toast.makeText(getActivity(), "Health data saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to save health data", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearHealthData() {
        databaseHelper.clearDatabase();
        Toast.makeText(getActivity(), "All health data cleared", Toast.LENGTH_SHORT).show();
    }
}
