package com.elegion.data.repository;

import com.elegion.data.api.BehanceApi;
import com.elegion.domain.model.user.UserResponse;
import com.elegion.domain.repository.ProfileRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class ProfileServerRepository implements ProfileRepository {

    @Inject
    BehanceApi mApi;

    @Override
    public Single<UserResponse> getUser(String username) {
       return mApi.getUserInfo(username);
    }
    @Inject
    public ProfileServerRepository() {}

    @Override
    public void insertUser(UserResponse response) {
        // На сервере нет сохранения user-ов
    }
}
