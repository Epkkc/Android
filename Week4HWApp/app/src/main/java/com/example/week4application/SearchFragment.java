package com.example.week4application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    private EditText text;
    private Button search_button;
    private SharedPreferences sharedPreferences;
    private String textForSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        text = view.findViewById(R.id.editText);
        search_button = view.findViewById(R.id.search_button);
        sharedPreferences = getActivity().getSharedPreferences(SettingsFragment.NAME, Context.MODE_PRIVATE);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                textForSearch = text.getText().toString();
                switch (sharedPreferences.getInt(SettingsFragment.KEY, R.id.rGoogle)){
                    case (R.id.rGoogle):
                        intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/search?q=" + textForSearch));
                        break;
                    case (R.id.rYandex):
                        intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://yandex.ru/search/?text=" + textForSearch));
                        break;
                    case (R.id.rBing):
                        intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.bing.com/search?q=" + textForSearch));
                        break;
                }
                startActivity(intent);
            }
        });

        return view;
    }
}
