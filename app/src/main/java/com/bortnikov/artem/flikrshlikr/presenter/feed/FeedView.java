package com.bortnikov.artem.flikrshlikr.presenter.feed;

import com.arellomobile.mvp.MvpView;

public interface FeedView extends MvpView{

    void startLoad();

    void finishLoad();

    void showError(Throwable e);

    void updateList();

    void setTitle(String s);

}
