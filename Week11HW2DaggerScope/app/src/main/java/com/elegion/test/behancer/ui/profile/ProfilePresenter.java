package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter {

    public static final String TAG = ProfileView.class.getSimpleName();


    private ProfileView mView;
    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;

    {
        AppDelegate.getAppComponent().inject(this);
    }

    public void setView(ProfileView view){
        mView = view;
    }


    public void getProfile(String mUsername) {
        mCompositeDisposable.add(mApi.getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showRefresh())
                .doFinally(() -> mView.hideRefresh())
                .subscribe(
                        response -> {
                            mView.showProfile();
                            mView.bind(response.getUser());
                        },
                        throwable -> mView.showError()));
    }

    public void setPicture(Context context, String URL) throws IOException {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mView.setPicture(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.i(TAG, "onBitmapFailed: Failed download");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(context)
                .load(URL)
                .into(target);
    }


}
