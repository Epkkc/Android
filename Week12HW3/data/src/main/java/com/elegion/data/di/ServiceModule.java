package com.elegion.data.di;

import com.elegion.data.repository.ProfileRepository;
import com.elegion.data.repository.ProjectRepository;
import com.elegion.data.services.ProfileService;
import com.elegion.data.services.ProfileServiceImpl;
import com.elegion.data.services.ProjectService;
import com.elegion.data.services.ProjectServiceImpl;

import javax.inject.Inject;
import javax.inject.Named;

import toothpick.config.Module;


/**
 * Created by tanchuev on 23.04.2018.
 */


public class ServiceModule extends Module {



    public ServiceModule() {
        bind(ProjectService.class).toInstance(new ProjectServiceImpl());
        bind(ProfileService.class).toInstance(new ProfileServiceImpl());
    }

//    ProjectService provideProjectService(ProjectServiceImpl projectService) {
//        return projectService;
//    }
//
//
//    ProfileService provideProfileService(ProfileServiceImpl profileService) {
//        return profileService;
//    }
}
