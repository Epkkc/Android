package com.elegion.test.behancer.ui.projects;

import android.view.View;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsPresenter extends BasePresenter {

    private ProjectsView mView;
    private Storage mStorage;

    public ProjectsPresenter(ProjectsView mView, Storage mStorage) {
        this.mView = mView;
        this.mStorage = mStorage;
    }

    public void getProjects(){
        mCompositDisposable.add(
        ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> mStorage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .subscribe(
                        response -> {
                            mView.showProjects(response.getProjects());
                        },
                        throwable -> {
                            mView.showError();
                        }));

    }

    public void openProfileFragment(String username){
        mView.openProfileFragment(username);

    }
}


