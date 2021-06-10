package com.example.gitapi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.model.User;
import com.squareup.picasso.Picasso;

public class UserHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView usernameTView;
    private TextView followersTView;

    public UserHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.avatar_img);
        usernameTView = itemView.findViewById(R.id.username);
        followersTView = itemView.findViewById(R.id.followers_number);
    }

    public void bind(User user, OnRecyclerItemClick listener){

        Picasso.with(imageView.getContext())
                .load(user.getAvatarURL())
                .fit()
                .into(imageView);

        usernameTView.setText(user.getLogin());
        //followersTView.setText(user.getFollowersNumber());

        itemView.setOnClickListener(v -> listener.onItemClick(user.getLogin()));

    }

}
