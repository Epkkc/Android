package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;

import com.elegion.data.Storage;
import com.elegion.data.api.BehanceApi;
import com.elegion.domain.ApiUtils;
import com.elegion.domain.service.ProfileService;
import com.elegion.test.behancer.AppDelegate;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter {

    private ProfileView profileView;
    private String mUsername;

    @Inject
    ProfileService service;

    private Disposable mDisposable;

    @Inject
    public ProfilePresenter() {
    }


    public void loadProfiles() {
        mDisposable = service.loadProfiles(mUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> profileView.showRefresh())
                .doFinally(() -> profileView.hideRefresh())
                .subscribe(
                        response -> {profileView.bind(response.getUser());},
                        throwable -> {profileView.showError();});
    }

    public void loadPicture(String pictureURl, Context context, Target target){
        Picasso.with(context).load(pictureURl).into(target);
    }

    public void dispose(){
        mDisposable.dispose();
    }

    public void setProfileView(ProfileView profileView) {
        this.profileView = profileView;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }
}
