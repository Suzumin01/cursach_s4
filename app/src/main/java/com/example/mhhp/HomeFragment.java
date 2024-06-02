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

    private TextView greetingTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        greetingTextView = view.findViewById(R.id.greetingTextView);

        // Загрузка имени и отчества пользователя из SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
        String firstName = preferences.getString("firstName", "User");
        String middleName = preferences.getString("middleName", "");

        greetingTextView.setText("Здравствуйте, " + firstName + " " + middleName + "!");

        // Здесь добавить логику для вычисления и отображения средних показателей

        return view;
    }
}

