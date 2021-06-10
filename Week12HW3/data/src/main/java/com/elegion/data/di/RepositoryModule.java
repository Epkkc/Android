package com.elegion.data.di;

import com.elegion.data.repository.ProfileDBRepository;
import com.elegion.data.repository.ProfileServerRepository;
import com.elegion.data.repository.ProjectDBRepository;
import com.elegion.data.repository.ProjectServerRepository;
import com.elegion.data.repository.ProfileRepository;
import com.elegion.data.repository.ProjectRepository;
import toothpick.config.Module;

import javax.inject.Inject;

/**
 * Created by tanchuev on 23.04.2018.
 */

public class RepositoryModule extends Module {

    public RepositoryModule() {
        bind(ProjectRepository.class).withName(ProjectRepository.PROJECT_SERVER).toInstance(new ProjectServerRepository());
        bind(ProjectRepository.class).withName(ProjectRepository.PROJECT_DB).toInstance( new ProjectDBRepository());
        bind(ProfileRepository.class).withName(ProfileRepository.PROFILE_SERVER).toInstance(new ProfileServerRepository());
        bind(ProfileRepository.class).withName(ProfileRepository.PROFILE_DB).toInstance(new ProfileDBRepository());
    }

}
