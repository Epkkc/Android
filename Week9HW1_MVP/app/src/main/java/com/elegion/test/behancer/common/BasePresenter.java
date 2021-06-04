package com.elegion.test.behancer.common;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter {

    protected CompositeDisposable mCompositDisposable = new CompositeDisposable();

    public void disposeAll(){
        mCompositDisposable.clear();
    }
}
