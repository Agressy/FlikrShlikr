package com.bortnikov.artem.flikrshlikr.presenter.feed;

import com.arellomobile.mvp.InjectViewState;
import com.bortnikov.artem.flikrshlikr.data.Endpoints;
import com.bortnikov.artem.flikrshlikr.di.AppComponent;

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

import io.realm.Realm;
import io.realm.RealmResults;

@InjectViewState
public class FeedPresenter extends BaseRestPresenter<FeedList, FeedView> {

    @Inject
    Endpoints netApi;

    private List<RealmModel> modelList = new ArrayList<>();

    private Realm realm;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MainApp.getComponent().injectsToFeedPresenter(this);
    }

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);

        loadData();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }

    private void loadData() {
        getViewState().startLoading();
        NetApiClient.getInstance().getFeed(netApi).subscribe(this);
    }

    private void readFromRealm() {
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<RealmModel> realmList = realm.where(RealmModel.class).findAll();
            getViewState().setItems(realmList);
        } catch (Exception e) {
            getViewState().showError(e);
        }
    }

    private void saveToRealm() {
        try {
            realm = Realm.getDefaultInstance();

            for (RealmModel curItem : modelList) {
                try {
                    realm.beginTransaction();
                    RealmModel realmModel = realm.createObject(RealmModel.class);
                    realmModel.setId(curItem.getId());
                    realmModel.setTitle(curItem.getTitle());
                    realmModel.setImageUrl(curItem.getImageUrl());
                    realm.commitTransaction();
                } catch (Exception e) {
                    realm.cancelTransaction();
                }
            }
        } catch (Exception e) {
            getViewState().showError(e);
        }
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
        readFromRealm();
        getViewState().hideLoading();
    }

    @Override
    public void onComplete() {
        getViewState().hideLoading();
        saveToRealm();
    }
}
