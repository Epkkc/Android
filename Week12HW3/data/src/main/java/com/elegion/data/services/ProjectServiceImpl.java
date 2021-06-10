package com.elegion.data.services;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.elegion.data.AppDelegate;
import com.elegion.data.repository.ProjectRepository;
import com.elegion.domain.ApiUtils;
import com.elegion.data.model.project.Project;
import com.elegion.data.model.project.ProjectResponse;
import com.elegion.data.model.project.RichProject;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import toothpick.Scope;
import toothpick.Toothpick;

/**
 * Created by tanchuev on 24.04.2018.
 */

public class ProjectServiceImpl implements ProjectService {

    public ProjectServiceImpl() {}

    @Inject
    @Named(ProjectRepository.PROJECT_SERVER)
    ProjectRepository mServerRepository;

    @Inject
    @Named(ProjectRepository.PROJECT_DB)
    ProjectRepository mDBRepository;

//    private ProjectRepository mServerRepository;
//    private ProjectRepository mDBRepository;

//    public ProjectServiceImpl(ProjectRepository mServerRepository, ProjectRepository mDBRepository) {
//        this.mServerRepository = mServerRepository;
//        this.mDBRepository = mDBRepository;
//    }

    @Override
    public void binding() {
        Toothpick.inject(this, AppDelegate.getAppScope());
        mServerRepository.binding();
        mDBRepository.binding();
    }

    @Override
    public Single<ProjectResponse> getProjects() {
        return mServerRepository.getProjects()
                .doOnSuccess(projectResponse -> mDBRepository.insertProjects(projectResponse.getProjects()))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())
                                ? mDBRepository.getProjects().blockingGet()
                                : null);
    }

    @Override
    public void insertProjects(List<Project> projects) {
        mDBRepository.insertProjects(projects);
    }

    @Override
    public LiveData<PagedList<RichProject>> getProjectsPaged() {
        return mDBRepository.getProjectsPaged();
    }
}
