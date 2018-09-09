package com.bortnikov.artem.flikrshlikr.presenter;

import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestPresenter;
import rx.Observable;


public class RealRestPresenter extends BaseRestPresenter<String> {

    @Override
    public void onNext(String s) {

    }

    public void update(){
        getViewState().startLoading();
        Observable.just("", "")
                .subscribe(this);
    }
}

