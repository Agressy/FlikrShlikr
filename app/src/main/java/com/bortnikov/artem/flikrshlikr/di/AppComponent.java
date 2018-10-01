package com.bortnikov.artem.flikrshlikr.di;

import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.search.SearchPresenter;

import dagger.Component;

@Component(modules = {DaggerNetModule.class})
public interface AppComponent {
    void injectsToFeedPresenter(FeedPresenter feedPresenter);

    void injectsToSearchPresenter(SearchPresenter searchPresenter);
}