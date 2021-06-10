package com.example.gitapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.data.ProfileActivityPresenter;

public class ProfileActivity extends Activity {

    private ProfileActivityPresenter presenter;

    private ImageView imageView;
    private TextView usernameTView;
    private TextView followersTView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageView = findViewById(R.id.avatar_img);
        usernameTView = findViewById(R.id.username);
        followersTView = findViewById(R.id.followers_number);

        presenter = new ProfileActivityPresenter();

    }
}
