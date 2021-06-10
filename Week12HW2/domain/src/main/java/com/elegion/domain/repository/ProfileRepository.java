package com.elegion.domain.repository;

import com.elegion.domain.model.user.UserResponse;

import io.reactivex.Single;

public interface ProfileRepository {

    String PROFILE_SERVER = "PROFILE_SERVER";
    String PROFILE_DB = "PROFILE_DB";

    Single<UserResponse> getUser(String username);

    void insertUser(UserResponse response);
}
