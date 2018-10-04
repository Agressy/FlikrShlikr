package com.bortnikov.artem.flikrshlikr.presenter.feed;

import com.arellomobile.mvp.MvpView;
import com.bortnikov.artem.flikrshlikr.model.RealmModel;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestView;

import java.util.List;

public interface FeedView extends BaseRestView {

    void setItems(List<RealmModel> items);
}
