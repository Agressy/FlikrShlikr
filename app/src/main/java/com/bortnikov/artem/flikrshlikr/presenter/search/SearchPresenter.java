package com.bortnikov.artem.flikrshlikr.presenter.search;

import com.arellomobile.mvp.InjectViewState;
import com.bortnikov.artem.flikrshlikr.data.Endpoints;

import com.bortnikov.artem.flikrshlikr.MainApp;
import com.bortnikov.artem.flikrshlikr.model.RealmModel;
import com.bortnikov.artem.flikrshlikr.model.retrofit.Photo;
import com.bortnikov.artem.flikrshlikr.model.retrofit.FeedList;
import com.bortnikov.artem.flikrshlikr.data.rest.NetApiClient;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestPresenter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class SearchPresenter extends BaseRestPresenter<FeedList, SearchingView> {

    @Inject
    Endpoints netApi;

    private List<RealmModel> modelList = new ArrayList<>();

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
        NetApiClient.getInstance().getSearch(netApi, s).subscribe(this);
    }

    private void loadData() {
        getViewState().startLoading();
        NetApiClient.getInstance().getSearch(netApi, "manchester united").subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(FeedList items) {
        List<Photo> list;
        list = items.getPhotos().getPhoto();
        for (Photo f : list) {
            RealmModel realmItem = new RealmModel();
            realmItem.setId(0);
            realmItem.setTitle(String.valueOf(f.getTitle()));
            realmItem.setImageUrl(String.valueOf(f.getUrlS()));
            modelList.add(realmItem);
        }
        getViewState().setItems(modelList);
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
