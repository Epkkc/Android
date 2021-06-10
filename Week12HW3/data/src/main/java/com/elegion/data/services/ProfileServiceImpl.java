package com.elegion.data.services;

import com.elegion.data.AppDelegate;
import com.elegion.data.repository.ProfileRepository;
import com.elegion.domain.ApiUtils;
import com.elegion.data.model.user.UserResponse;


import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import toothpick.Toothpick;

public class ProfileServiceImpl implements ProfileService {

    public ProfileServiceImpl() {}

    @Inject
    @Named(ProfileRepository.PROFILE_SERVER)
    ProfileRepository mServerRepository;

    @Inject
    @Named(ProfileRepository.PROFILE_DB)
    ProfileRepository mDBRepository;


    @Override
    public Single<UserResponse> loadProfiles(String username) {
        return mServerRepository.getUser(username)
                .doOnSuccess(response -> mDBRepository.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mDBRepository.getUser(username).blockingGet() :
                                null);
    }

    @Override
    public void binding() {
        Toothpick.inject(this, AppDelegate.getAppScope());
        mServerRepository.binding();
        mDBRepository.binding();
    }


}
