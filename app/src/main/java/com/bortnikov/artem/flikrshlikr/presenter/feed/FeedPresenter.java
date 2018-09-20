package com.bortnikov.artem.flikrshlikr.presenter.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
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

@InjectViewState
public class FeedPresenter extends MvpPresenter<FeedView> implements Subscriber<FeedList> {

    List<RealmModel> modelList = new ArrayList<>();

    Realm realm;

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
        return listPrestenter;
    }

    private ListPresenter listPrestenter = new ListPresenter();

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);
        loadData();
    }

    private void loadData() {
        getViewState().startLoad();
        NetApiClient.getInstance().getFeed().subscribe(this);
    }

    public void saveToRealm() {
        Single<List<RealmModel>> saveToRealm = Single.create((SingleOnSubscribe<List<RealmModel>>) emitter -> {
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
                        emitter.onError(e);
                    }
                }

                List<RealmModel> list = new ArrayList<>(realm.where(RealmModel.class).findAll());
                emitter.onSuccess(list);
                realm.close();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        saveToRealm.subscribeWith(CreateObserver());
    }

    private DisposableSingleObserver<List<RealmModel>> CreateObserver() {
        return new DisposableSingleObserver<List<RealmModel>>() {

            @Override
            public void onSuccess(@NonNull List<RealmModel> list) {
                getViewState().setTitle(list.get(0).getTitle());
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        };
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(FeedList items) {
        List<Photo> list;
        list = items.getPhotos().getPhoto();
        listPrestenter.titles.clear();
        listPrestenter.images.clear();
        for (Photo f : list) {
            listPrestenter.titles.add(String.valueOf(f.getTitle()));
            listPrestenter.images.add(String.valueOf(f.getUrlS()));
            modelList.add(new RealmModel(String.valueOf(f.getTitle()), String.valueOf(f.getUrlS())));
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
