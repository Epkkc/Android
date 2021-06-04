package com.elegion.test.behancer.ui.projects;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    private Storage mStorage;
    private String username;

    public ProjectsPresenter(Storage mStorage, String username) {
        this.mStorage = mStorage;
        this.username = username;
    }

    public void getProjects() {
        if (username.equals(ProjectsFragment.EMPTY_AUTHOR)) {

            mCompositDisposable.add(
                    ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                            .doOnSuccess(response -> mStorage.insertProjects(response))
                            .onErrorReturn(throwable ->
                                    ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showLoading())
                            .doFinally(() -> getViewState().hideLoading())
                            .subscribe(
                                    response -> {
                                        getViewState().showProjects(response.getProjects());
                                    },
                                    throwable -> {
                                        getViewState().showError();
                                    }));
        } else {
            mCompositDisposable.add(
                    ApiUtils.getApiService().getAuthorProjects(username)

                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showLoading())
                            .doFinally(() -> getViewState().hideLoading())
                            .subscribe(
                                    response -> {
                                        getViewState().showProjects(response.getProjects());
                                    },
                                    throwable -> {
                                        getViewState().showError();
                                    }));
        }

    }

    public void openProfileFragment(String username, int mode) {
        if (mode == ProjectsFragment.NO_AUTHOR) {
            getViewState().openProfileFragment(username);
        }

    }
}


