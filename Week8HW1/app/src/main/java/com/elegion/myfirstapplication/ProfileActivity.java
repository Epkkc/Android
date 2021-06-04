package com.elegion.myfirstapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.elegion.myfirstapplication.Model.NewUser;

public class ProfileActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_KEY";

    private final View.OnClickListener mOnPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        AppCompatImageView mPhoto = findViewById(R.id.ivPhoto);
        TextView mLogin = findViewById(R.id.tvEmail);
        TextView mPassword = findViewById(R.id.tvPassword);

        Bundle bundle = getIntent().getExtras();
        NewUser mUser = null;
        if (bundle != null){
            mUser = (NewUser) bundle.get(USER_KEY);
        }
        if (mUser != null) {
            mLogin.setText(mUser.getData().getEmail());
            mPassword.setText(mUser.getData().getName());
        }

        mPhoto.setOnClickListener(mOnPhotoClickListener);
    }
}
