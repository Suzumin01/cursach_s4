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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private EditText firstNameInput, middleNameInput;
    private Button saveProfileButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firstNameInput = view.findViewById(R.id.firstNameInput);
        middleNameInput = view.findViewById(R.id.middleNameInput);
        saveProfileButton = view.findViewById(R.id.saveProfileButton);

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });

        // Загрузка сохраненных данных профиля
        SharedPreferences preferences = getActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
        firstNameInput.setText(preferences.getString("firstName", ""));
        middleNameInput.setText(preferences.getString("middleName", ""));

        return view;
    }

    private void saveUserProfile() {
        String firstName = firstNameInput.getText().toString();
        String middleName = middleNameInput.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("middleName", middleName);
        editor.apply();

        Toast.makeText(getActivity(), "Profile saved", Toast.LENGTH_SHORT).show();
    }
}
