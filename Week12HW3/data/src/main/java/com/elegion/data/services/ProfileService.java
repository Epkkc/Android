package com.elegion.data.services;

import com.elegion.data.model.user.UserResponse;

import io.reactivex.Single;

public interface ProfileService {

    Single<UserResponse> loadProfiles(String username);

    void binding();

}
