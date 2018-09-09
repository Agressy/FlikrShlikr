package com.bortnikov.artem.flikrshlikr.presenter.base;

import com.arellomobile.mvp.MvpPresenter;

import rx.Observer;

public abstract class BaseRestPresenter<T> extends MvpPresenter<BaseRestView> implements Observer<T> {

    @Override
    public void onCompleted() {
        getViewState().hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        getViewState().showError(e.getLocalizedMessage());
    }
}
