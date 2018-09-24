package com.bortnikov.artem.flikrshlikr.presenter.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bortnikov.artem.flikrshlikr.AppComponentSingleton;
import com.bortnikov.artem.flikrshlikr.MainApplication;
import com.bortnikov.artem.flikrshlikr.data.model.RealmModel;
import com.bortnikov.artem.flikrshlikr.data.model.retrofit.Photo;
import com.bortnikov.artem.flikrshlikr.data.model.retrofit.FeedList;
import com.bortnikov.artem.flikrshlikr.data.rest.NetApiClient;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

@InjectViewState
public class FeedPresenter extends MvpPresenter<FeedView> implements Subscriber<FeedList> {

    private List<RealmModel> modelList = new ArrayList<>();

    private AppComponentSingleton appComponent;

    private Realm realm;


    class ListPresenter implements FeedListPresenter {
        List<String> titles = new ArrayList<>();
        List<String> images = new ArrayList<>();

        @Override
        public void bindView(FeedListItemView view) {
            view.setText(titles.get(view.getPos()));
            view.setImage(images.get(view.getPos()));
        }

        @Override
        public int getViewCount() {
            return titles.size();
        }
    }

    public FeedListPresenter getListFiles() {
        return listPresenter;
    }

    private ListPresenter listPresenter = new ListPresenter();

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);
        appComponent = MainApplication.getComponentSingleton();
        loadData();
    }

    private void loadData() {
        getViewState().startLoad();
        NetApiClient.getInstance().getFeed().subscribe(this);
    }

    public FeedListPresenter readFromRealm() {
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<RealmModel> tempList = realm.where(RealmModel.class).findAll();
            listPresenter.titles.clear();
            listPresenter.images.clear();
            for (RealmModel m : tempList) {
                listPresenter.titles.add(String.valueOf(m.getTitle()));
                listPresenter.images.add(String.valueOf(m.getImageUrl()));
            }
            realm.close();
        } catch (Exception e) {
            getViewState().showError(e);
        }
        return listPresenter;
    }

    public void saveToRealm() {
        try {
            realm = Realm.getDefaultInstance();

            for (RealmModel curItem : modelList) {
                try {
                    realm.beginTransaction();
                    RealmModel realmModel = realm.createObject(RealmModel.class);
                    realmModel.setTitle(curItem.getTitle());
                    realmModel.setImageUrl(curItem.getImageUrl());
                    realm.commitTransaction();
                } catch (Exception e) {
                    realm.cancelTransaction();
                }
            }
            realm.close();
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
        listPresenter.titles.clear();
        listPresenter.images.clear();
        for (Photo f : list) {
            listPresenter.titles.add(String.valueOf(f.getTitle()));
            listPresenter.images.add(String.valueOf(f.getUrlS()));
            RealmModel realmItem = new RealmModel();
            realmItem.setTitle(String.valueOf(f.getTitle()));
            realmItem.setImageUrl(String.valueOf(f.getUrlS()));
            modelList.add(realmItem);
        }
        getViewState().updateList();
    }


    @Override
    public void onError(Throwable e) {
        getViewState().showError(e);
        getViewState().finishLoad();
    }

    @Override
    public void onComplete() {
        getViewState().finishLoad();
    }
}
