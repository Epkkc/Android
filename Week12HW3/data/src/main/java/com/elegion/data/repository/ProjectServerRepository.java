package com.elegion.data.repository;


import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.elegion.data.AppDelegate;
import com.elegion.data.BuildConfig;
import com.elegion.data.api.BehanceApi;
import com.elegion.data.model.project.Project;
import com.elegion.data.model.project.ProjectResponse;
import com.elegion.data.model.project.RichProject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import toothpick.Toothpick;

/**
 * Created by tanchuev on 24.04.2018.
 */

public class ProjectServerRepository implements ProjectRepository {

    @Inject
    BehanceApi mApi;

    public ProjectServerRepository() {
    }

    @Override
    public Single<ProjectResponse> getProjects() {
        return mApi.getProjects(BuildConfig.API_QUERY);
    }

    @Override
    public void insertProjects(List<Project> projects) {
        //do nothing
    }

    @Override
    public LiveData<PagedList<RichProject>> getProjectsPaged() {
        return null;
    }

    @Override
    public void binding() {
        Toothpick.inject(this, AppDelegate.getAppScope());
    }


}
