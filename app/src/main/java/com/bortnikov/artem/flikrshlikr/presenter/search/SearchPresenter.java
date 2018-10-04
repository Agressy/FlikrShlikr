package com.bortnikov.artem.flikrshlikr.presenter.search;

import com.arellomobile.mvp.InjectViewState;
import com.bortnikov.artem.flikrshlikr.data.Endpoints;

import com.bortnikov.artem.flikrshlikr.MainApp;
import com.bortnikov.artem.flikrshlikr.data.model.realm.RealmModel;
import com.bortnikov.artem.flikrshlikr.data.model.retrofit.FeedList;
import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;
import com.bortnikov.artem.flikrshlikr.data.usecases.FeedUseCase;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestPresenter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class SearchPresenter extends BaseRestPresenter<ArrayList<DataViewModel>, SearchingView> {

    @Inject
    FeedUseCase usecase;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MainApp.getComponent().injectsToSearchPresenter(this);
    }

    @Override
    public void attachView(SearchingView view) {
        super.attachView(view);

        loadData();
    }

    public void searchNewInfo(String s) {
        getViewState().startLoading();
        usecase.getSearch(s).subscribe(this);
    }

    private void loadData() {
        getViewState().startLoading();
        usecase.getSearch("various").subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(ArrayList<DataViewModel> items) {
        getViewState().setItems(items);
    }

    @Override
    public void onError(Throwable e) {
        getViewState().showError(e);
        getViewState().hideLoading();
    }

    @Override
    public void onComplete() {
        getViewState().updateList();
        getViewState().hideLoading();
    }
}
