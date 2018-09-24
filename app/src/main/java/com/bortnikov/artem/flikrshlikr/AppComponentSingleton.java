package com.bortnikov.artem.flikrshlikr;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponentSingleton {

    Context appContext();

    void inject(MainApplication mainApp);
}