package com.elegion.domain.service;

import com.elegion.domain.model.user.UserResponse;

import io.reactivex.Single;

public interface ProfileService {

    Single<UserResponse> loadProfiles(String username);

}
