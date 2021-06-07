package com.elegion.test.behancer.di;

import com.elegion.test.behancer.ui.profile.ProfilePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfilePresenterModule {


    @Provides
    @Scopes.ProfileScope
    public ProfilePresenter provideProfilePresenter(){
        return new ProfilePresenter();
    }

}
