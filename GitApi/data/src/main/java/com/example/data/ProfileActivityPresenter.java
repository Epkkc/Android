package com.example.data;

import android.content.Context;

import com.example.domain.model.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityPresenter {

    ProfileActivityInterface view;

    public void downloadUserInfo(String username){

        Utils.getAPIService().getUserInfo(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.updateInfo(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }

    public void downloadImg(Context context, Target target, String url){
        Picasso.with(context).load(url).into(target);
    }



    public void setView(ProfileActivityInterface view) {
        this.view = view;
    }
}
