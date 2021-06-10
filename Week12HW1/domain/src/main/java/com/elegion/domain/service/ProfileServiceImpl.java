package com.elegion.domain.service;

import com.elegion.domain.ApiUtils;
import com.elegion.domain.model.user.UserResponse;
import com.elegion.domain.repository.ProfileRepository;
import com.elegion.domain.repository.ProjectRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;

public class ProfileServiceImpl implements ProfileService{


    @Inject
    @Named(ProfileRepository.PROFILE_SERVER)
    ProfileRepository mServerRepository;

    @Inject
    @Named(ProfileRepository.PROFILE_DB)
    ProfileRepository mDBRepository;

    @Inject
    public ProfileServiceImpl() {}


    @Override
    public Single<UserResponse> loadProfiles(String username) {
        return mServerRepository.getUser(username)
                .doOnSuccess(response -> mDBRepository.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mDBRepository.getUser(username).blockingGet() :
                                null);
    }


}
