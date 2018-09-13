package com.bortnikov.artem.flikrshlikr.presenter;


import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestView;

import io.reactivex.Flowable;


public class RealRestPresenter extends BaseRestPresenter<String,BaseRestView> {

    @Override
    public void onNext(String s) {

    }

    public void update(){
        getViewState().startLoading();
        Flowable.just("", "")
                .subscribe(this);
    }
}


