package com.example.week4application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    public static String NAME = "SHP";
    public static String KEY = "Option";

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        radioGroup =view.findViewById(R.id.rg);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);
                sharedPreferences.edit().putInt(KEY, checkedId).apply();
                Toast.makeText(getContext(),radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        sharedPreferences = getActivity().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        radioGroup.check(sharedPreferences.getInt(KEY, R.id.rGoogle));

        return view;
    }

}
