package com.bortnikov.artem.flikrshlikr.presenter.search;

import com.arellomobile.mvp.MvpView;
import com.bortnikov.artem.flikrshlikr.model.RealmModel;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestView;

import java.util.List;

public interface SearchingView extends BaseRestView {

    void setItems(List<RealmModel> items);

    void updateList();
}