package com.bortnikov.artem.flikrshlikr.di;

import com.bortnikov.artem.flikrshlikr.data.usecases.FeedUseCase;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.search.SearchPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DaggerNetModule.class, FeedUseCaseModule.class})
public interface AppComponent {
    FeedUseCase feedUseCase();

    void injectsToFeedPresenter(FeedPresenter feedPresenter);

    void injectsToSearchPresenter(SearchPresenter searchPresenter);
}