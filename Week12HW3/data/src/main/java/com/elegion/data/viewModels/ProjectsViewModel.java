package com.elegion.data.viewModels;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.elegion.data.AppDelegate;
import com.elegion.data.model.project.RichProject;
import com.elegion.data.services.ProjectService;
import com.elegion.data.uiComponents.OnItemClickListenerInterface;


import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

public class ProjectsViewModel extends ViewModel {

    @Inject
    ProjectService mService;

    public ProjectsViewModel() {}

    private Disposable mDisposable;
    private OnItemClickListenerInterface onItemClickListener;
    private LiveData<PagedList<RichProject>> projects;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isErrorVisible = new MutableLiveData<>();
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = this::updateProjects;


    public void setup(){
        Toothpick.inject(this, AppDelegate.getAppScope());
        mService.binding();
        projects = mService.getProjectsPaged();
        updateProjects();
    }


    private void updateProjects() {
        mDisposable = mService.getProjects()
                //.map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> isLoading.postValue(true))
                .doFinally(() -> isLoading.postValue(false))
                .doOnSuccess(response -> isErrorVisible.postValue(false))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> mService.insertProjects(response.getProjects()),
                        throwable -> {
                            boolean value = projects.getValue() == null || projects.getValue().size() == 0;
                            isErrorVisible.postValue(value);
                        });

    }

    public OnItemClickListenerInterface getOnItemClickListener() {
        return onItemClickListener;
    }

    public LiveData<PagedList<RichProject>> getProjects() {
        return projects;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return isErrorVisible;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return onRefreshListener;
    }

    public void setOnItemClickListener(OnItemClickListenerInterface onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
