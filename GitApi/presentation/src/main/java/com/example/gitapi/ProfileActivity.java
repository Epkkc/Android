package com.example.gitapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.data.ProfileActivityInterface;
import com.example.data.ProfileActivityPresenter;
import com.example.domain.model.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ProfileActivity extends Activity implements ProfileActivityInterface {

    private ProfileActivityPresenter presenter;

    private ImageView imageView;
    private TextView usernameTView;
    private TextView bioTView;
    private TextView typeTView;
    private TextView companyTView;
    private TextView followersTView;
    private Button btn;

    private String username;

    private final Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private final View.OnClickListener listener = v -> {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(ListActivity.USERNAME_KEY, username);
        startActivity(intent);
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        username = getIntent().getStringExtra(ListActivity.USERNAME_KEY);

        imageView = findViewById(R.id.profile_img);
        usernameTView = findViewById(R.id.profile_username);
        bioTView = findViewById(R.id.profile_bio);
        typeTView = findViewById(R.id.profile_type);
        companyTView = findViewById(R.id.profile_company);
        followersTView = findViewById(R.id.profile_followers);
        btn = findViewById(R.id.profile_btn);
        presenter = new ProfileActivityPresenter();
        presenter.setView(this);

        btn.setOnClickListener(listener);

        presenter.downloadUserInfo(username);
    }

    @Override
    public void updateInfo(User user) {
        presenter.downloadImg(this, target, user.getAvatarURL());

        usernameTView.setText(user.getLogin());
        bioTView.setText(user.getBio());
        typeTView.setText(user.getType());
        companyTView.setText(user.getCompany());
        followersTView.setText(user.getFollowersNumber()+"");
    }
}
