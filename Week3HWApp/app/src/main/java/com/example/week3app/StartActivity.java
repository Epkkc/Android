package com.example.week3app;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    EditText textInput;
    Button buttonStart;

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(textInput.getText())){
                // По нажатию на button ничего не происходит
            } else {
                // Выоводим Toast с введённым текстом
                makeToastFromSring(textInput.getText().toString());
                // Запускаем SecondActivity
                Intent intent = new Intent(StartActivity.this, SecondActivity.class);
                intent.putExtra(SecondActivity.TEXT_KEY, textInput.getText().toString());
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_start);

        textInput = findViewById(R.id.text);
        buttonStart  = findViewById(R.id.button);

        buttonStart.setOnClickListener(buttonListener);

    }

    private void makeToastFromRes(@StringRes int string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
    private void makeToastFromSring(String string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
}