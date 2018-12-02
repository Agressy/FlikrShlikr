package com.bortnikov.artem.flikrshlikr.presenter.search;

import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;
import com.bortnikov.artem.flikrshlikr.presenter.base.BaseRestView;

import java.util.List;

public interface SearchingView extends BaseRestView {

    void setItems(List<DataViewModel> items);

    void updateList();
}