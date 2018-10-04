package com.bortnikov.artem.flikrshlikr;

import android.app.Application;

import com.bortnikov.artem.flikrshlikr.di.AppComponent;
import com.bortnikov.artem.flikrshlikr.di.DaggerAppComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApp extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);

        component = DaggerAppComponent.create();
    }


    public static AppComponent getComponent() {
        return component;
    }
}