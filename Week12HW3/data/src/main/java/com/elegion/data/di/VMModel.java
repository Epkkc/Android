package com.elegion.data.di;

import com.elegion.data.services.ProfileService;
import com.elegion.data.services.ProjectService;
import com.elegion.data.viewModels.ProfileViewModel;
import com.elegion.data.viewModels.ProjectsViewModel;

import javax.inject.Inject;

import toothpick.config.Module;

public class VMModel extends Module {

    public VMModel() {
        bind(String.class).withName("TestName").toInstance("TestFromVMModule");
        bind(ProjectsViewModel.class).toInstance(new ProjectsViewModel());
        bind(ProfileViewModel.class).toInstance(new ProfileViewModel());
    }
}
