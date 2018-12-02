package com.bortnikov.artem.flikrshlikr.di;

import com.bortnikov.artem.flikrshlikr.data.ServiceGenerator;
import com.bortnikov.artem.flikrshlikr.data.Endpoints;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class DaggerNetModule {
    @Provides
    @Singleton
    Endpoints getEndpoints() {
        return new ServiceGenerator().createService(Endpoints.class);
    }
}