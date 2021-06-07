package com.elegion.test.behancer.di;

import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.profile.ProfilePresenter;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.ProjectsPresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    ProfilePresenterComponent getProfilePresenterComponent(ProfilePresenterModule profilePresenterModule);

    void inject(ProjectsFragment injector);

    void inject(ProfilePresenter profileFragment);

    void inject(ProjectsPresenter projectsPresenter);

}
