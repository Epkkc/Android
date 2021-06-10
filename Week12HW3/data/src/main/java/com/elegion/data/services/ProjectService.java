package com.elegion.data.services;



import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.elegion.data.model.project.Project;
import com.elegion.data.model.project.ProjectResponse;
import com.elegion.data.model.project.RichProject;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by tanchuev on 24.04.2018.
 */

public interface ProjectService {
    Single<ProjectResponse> getProjects();

    void insertProjects(List<Project> projects);

    LiveData<PagedList<RichProject>> getProjectsPaged();

    void binding();



}
