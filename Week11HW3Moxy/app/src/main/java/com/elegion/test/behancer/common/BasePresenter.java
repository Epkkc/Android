package com.elegion.test.behancer.common;


import android.view.View;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Vladislav Falzan.
 */

public abstract class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void disposeAll() {
            mCompositeDisposable.dispose();
    }
}
