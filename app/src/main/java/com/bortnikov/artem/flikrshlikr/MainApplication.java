package com.bortnikov.artem.flikrshlikr;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    private static AppComponentSingleton singletonComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);

        singletonComponent = DaggerAppComponentSingleton
                .builder()
                .appModule(new AppModule(this))
                .build();
        singletonComponent.inject(this);
    }


    public static AppComponentSingleton getComponentSingleton() {
        return singletonComponent;
    }
}