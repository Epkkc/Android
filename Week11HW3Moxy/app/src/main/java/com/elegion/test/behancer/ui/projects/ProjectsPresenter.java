package com.elegion.test.behancer.ui.projects;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;
import com.elegion.test.behancer.data.Storage;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vladislav Falzan.
 */
@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {


    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;

    {
        AppDelegate.getAppComponent().inject(this);
    }

    public void getProjects() {
        getViewState().showRefresh();
        mCompositeDisposable.add(
                mApi.getProjects(BuildConfig.API_QUERY)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertProjects)
                        .onErrorReturn(throwable ->
                                ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe(disposable -> getViewState().showRefresh())
//                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                response -> {
                                    getViewState().showProjects(response.getProjects());
                                    getViewState().hideRefresh();
                                },
                                throwable -> {
                                    getViewState().showError();
                                    getViewState().hideRefresh();
                                })
        );
    }

    public void openProfileFragment(String username) {
        getViewState().openProfileFragment(username);
    }
}
