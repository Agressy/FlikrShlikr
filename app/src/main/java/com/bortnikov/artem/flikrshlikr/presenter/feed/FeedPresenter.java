package com.bortnikov.artem.flikrshlikr.presenter.feed;

import com.arellomobile.mvp.InjectViewState;

import com.bortnikov.artem.flikrshlikr.MainApp;
import com.bortnikov.artem.flikrshlikr.data.model.realm.RealmModel;
import com.bortnikov.artem.flikrshlikr.data.model.retrofit.FeedList;
import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;
import com.bortnikov.artem.flikrshlikr.data.usecases.FeedUseCase;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestPresenter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

@InjectViewState
public class FeedPresenter extends BaseRestPresenter<Integer, FeedView> {

    @Inject
    FeedUseCase usecase;

    private Realm realm;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        realm = Realm.getDefaultInstance();
        MainApp.getComponent().injectsToFeedPresenter(this);
        loadData();
    }

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);
        setData();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }

    private void loadData() {
        getViewState().startLoading();
        usecase.getFeed().subscribe(this);
    }

    private void setData() {
        RealmResults<RealmModel> feed = realm.where(RealmModel.class).findAll();
        ArrayList<DataViewModel> result = new ArrayList<>();
        for (RealmModel curItem : feed) {
            result.add(new DataViewModel(curItem));
        }
        getViewState().setItems(result);
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Integer o) {
        setData();
    }

    @Override
    public void onError(Throwable e) {
        getViewState().showError(e);
        getViewState().hideLoading();
    }

    @Override
    public void onComplete() {
        getViewState().hideLoading();
    }
}
