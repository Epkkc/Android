package com.elegion.data.repository;

import com.elegion.data.AppDelegate;
import com.elegion.data.api.BehanceApi;
import com.elegion.data.model.user.UserResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import toothpick.Toothpick;

public class ProfileServerRepository implements ProfileRepository {

    public ProfileServerRepository() {}

    @Inject
    BehanceApi mApi;

    @Override
    public Single<UserResponse> getUser(String username) {
       return mApi.getUserInfo(username);
    }

    @Override
    public void insertUser(UserResponse response) {
        // На сервере нет сохранения user-ов
    }

    @Override
    public void binding() {
        Toothpick.inject(this, AppDelegate.getAppScope());
    }
}
