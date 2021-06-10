package com.elegion.test.behancer.di;

import com.elegion.data.repository.ProfileDBRepository;
import com.elegion.data.repository.ProfileServerRepository;
import com.elegion.data.repository.ProjectDBRepository;
import com.elegion.data.repository.ProjectServerRepository;
import com.elegion.domain.repository.ProfileRepository;
import com.elegion.domain.repository.ProjectRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    @Named(ProjectRepository.PROJECT_SERVER)
    ProjectRepository provideProjectServerRepository(ProjectServerRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    @Named(ProjectRepository.PROJECT_DB)
    ProjectRepository provideProjectDBRepository(ProjectDBRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    @Named(ProfileRepository.PROFILE_SERVER)
    ProfileRepository provideProfileServerRepository(ProfileServerRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    @Named(ProfileRepository.PROFILE_DB)
    ProfileRepository provideProfileDBRepository(ProfileDBRepository repository) {
        return repository;
    }


}
