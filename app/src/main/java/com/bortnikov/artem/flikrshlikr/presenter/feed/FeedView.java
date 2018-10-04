package com.bortnikov.artem.flikrshlikr.presenter.feed;

import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestView;

import java.util.List;

public interface FeedView extends BaseRestView {

    void setItems(List<DataViewModel> items);
}
