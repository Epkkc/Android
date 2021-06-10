package com.elegion.data.viewModels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.elegion.data.AppDelegate;
import com.elegion.data.services.ProfileService;
import com.elegion.data.uiComponents.RefreshCallbackInterface;
import com.elegion.data.utils.DateUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

public class ProfileViewModel extends ViewModel {

    @Inject
    ProfileService service;

    ObservableField<Integer> errorVisible = new ObservableField<>(View.GONE);
    ObservableField<Integer> profileVisibility = new ObservableField<>(View.GONE);

    ObservableBoolean test = new ObservableBoolean(false);

    ObservableField<String> imageURL = new ObservableField<>();
    ObservableField<String> profileName = new ObservableField<>();
    ObservableField<String> profileCreatedOn = new ObservableField<>();
    ObservableField<String> profileLocation = new ObservableField<>();

    private String username;
    private Disposable mDisposable;

    public ProfileViewModel() {}

    public void setup(){
        Toothpick.inject(this, AppDelegate.getAppScope());
        service.binding();
    }

    public void loadProfile(RefreshCallbackInterface callback) {
        mDisposable = service.loadProfiles(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (callback != null) callback.callback(true);
                })
                .doFinally(() -> {
                    if (callback != null) callback.callback(false);
                })
                .subscribe(
                        response -> {
                            test.set(false);

                            errorVisible.set(View.GONE);
                            profileVisibility.set(View.VISIBLE);

                            imageURL.set(response.getUser().getImage().getPhotoUrl());
                            profileName.set(response.getUser().getDisplayName());
                            profileCreatedOn.set(DateUtils.format(response.getUser().getCreatedOn()));
                            profileLocation.set(response.getUser().getLocation());
                        },
                        throwable -> {
                            test.set(true);
                            errorVisible.set(View.VISIBLE);
                            profileVisibility.set(View.GONE);
                        });
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public ObservableField<Integer> getErrorVisible() {
        return errorVisible;
    }

    public ObservableField<Integer> getProfileVisibility() {
        return profileVisibility;
    }

    public ObservableBoolean getTest() {
        return test;
    }


    public ObservableField<String> getImageURL() {
        return imageURL;
    }

    public ObservableField<String> getProfileName() {
        return profileName;
    }

    public ObservableField<String> getProfileCreatedOn() {
        return profileCreatedOn;
    }

    public ObservableField<String> getProfileLocation() {
        return profileLocation;
    }


}


