package com.elegion.myfirstapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.elegion.myfirstapplication.Model.User;

public class ProfileActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_KEY";

    private AppCompatImageView mPhoto;
    private TextView mLogin;
    private TextView mPassword;
    private User mUser;

    private View.OnClickListener mOnPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mPhoto = findViewById(R.id.ivPhoto);
        mLogin = findViewById(R.id.tvEmail);
        mPassword = findViewById(R.id.tvPassword);

        Bundle bundle = getIntent().getExtras();
        mUser = (User) bundle.get(USER_KEY);
        mLogin.setText(mUser.getLogin());
        mPassword.setText(mUser.getPassword());

        mPhoto.setOnClickListener(mOnPhotoClickListener);
    }
}
