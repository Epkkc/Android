package com.elegion.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.v4.util.Pair;

import com.elegion.data.AppDelegate;
import com.elegion.data.database.BehanceDao;
import com.elegion.data.model.project.Cover;
import com.elegion.data.model.project.Owner;
import com.elegion.data.model.project.Project;
import com.elegion.data.model.project.ProjectResponse;
import com.elegion.data.model.project.RichProject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;
import toothpick.Toothpick;

/**
 * Created by tanchuev on 24.04.2018.
 */

public class ProjectDBRepository implements ProjectRepository {

    public static final int PAGE_SIZE = 10;

    @Inject
    BehanceDao mBehanceDao;

    public ProjectDBRepository() {}

    @Override
    public Single<ProjectResponse> getProjects() {
        return Single.fromCallable(new Callable<ProjectResponse>() {
            @Override
            public ProjectResponse call() throws Exception {
                ProjectResponse response = new ProjectResponse();
                List<Project> projects = mBehanceDao.getProjects();
                for (Project project : projects) {
                    project.setCover(mBehanceDao.getCoverFromProject(project.getId()));
                    project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
                }
                response.setProjects(projects);

                return response;
            }
        });
    }

    @Override
    public void insertProjects(List<Project> projects) {
        mBehanceDao.insertProjects(projects);

        Pair<List<Cover>, List<Owner>> assembled = assemble(projects);

        mBehanceDao.clearCoverTable();
        mBehanceDao.insertCovers(assembled.first);
        mBehanceDao.clearOwnerTable();
        mBehanceDao.insertOwners(assembled.second);
    }

    private Pair<List<Cover>, List<Owner>> assemble(List<Project> projects) {

        List<Cover> covers = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            Cover cover = projects.get(i).getCover();
            cover.setId(i);
            cover.setProjectId(projects.get(i).getId());
            covers.add(cover);

            Owner owner = projects.get(i).getOwners().get(0);
            owner.setId(i);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }
        return new Pair<>(covers, owners);
    }

    @Override
    public LiveData<PagedList<RichProject>> getProjectsPaged() {
        return new LivePagedListBuilder<>(mBehanceDao.getProjectsPaged(), PAGE_SIZE).build();
    }

    @Override
    public void binding() {
        Toothpick.inject(this, AppDelegate.getAppScope());
    }
}
