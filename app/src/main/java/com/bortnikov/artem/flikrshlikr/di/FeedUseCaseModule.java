package com.bortnikov.artem.flikrshlikr.di;

import com.bortnikov.artem.flikrshlikr.data.Endpoints;
import com.bortnikov.artem.flikrshlikr.data.usecases.FeedUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedUseCaseModule {

    @Provides
    @Singleton
    FeedUseCase feedUseCase(Endpoints endpoints) {
        return new FeedUseCase(endpoints);
    }
}
