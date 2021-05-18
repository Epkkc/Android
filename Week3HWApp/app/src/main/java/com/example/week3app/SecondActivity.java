package com.example.week3app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SecondActivity extends AppCompatActivity {
    public static String TEXT_KEY = "TEXT_KEY";
    private TextView mText;
    private Button mButton;
    private Bundle bundle;
    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + bundle.getString(TEXT_KEY)));
            //Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            //intent.putExtra(SearchManager.QUERY, bundle.getString(TEXT_KEY));
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_second);
        bundle = getIntent().getExtras();
        mText = findViewById(R.id.tvSecondAct);
        mButton = findViewById(R.id.buttonSecondAct);

        mText.setText(bundle.getString(TEXT_KEY));
        mButton.setOnClickListener(mButtonListener);
    }
}