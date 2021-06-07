package com.elegion.test.behancer.di;

import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.profile.ProfilePresenter;

import dagger.Component;
import dagger.Subcomponent;


@Scopes.ProfileScope
@Subcomponent(modules = {ProfilePresenterModule.class})
public interface ProfilePresenterComponent {

    void inject(ProfileFragment profileFragment);

}
