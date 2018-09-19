package com.bortnikov.artem.flikrshlikr.presenter.feed;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bortnikov.artem.flikrshlikr.data.model.Photo;
import com.bortnikov.artem.flikrshlikr.data.model.FeedList;
import com.bortnikov.artem.flikrshlikr.data.rest.NetApiClient;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class FeedPresenter extends MvpPresenter<FeedView> implements Subscriber<FeedList> {

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
        List<String> titlesList = new ArrayList<>();
        List<String> imagesList = new ArrayList<>();
        listPrestenter.titles.addAll(titlesList);
        listPrestenter.images.addAll(imagesList);
    }

    private void loadData() {
        getViewState().startLoad();
        NetApiClient.getInstance().getFeed().subscribe(this);
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
