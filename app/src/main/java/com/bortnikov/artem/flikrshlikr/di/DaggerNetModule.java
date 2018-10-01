package com.bortnikov.artem.flikrshlikr.di;

import com.bortnikov.artem.flikrshlikr.data.ServiceGenerator;
import com.bortnikov.artem.flikrshlikr.data.Endpoints;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerNetModule {
    @Provides
    Endpoints getEndpoints(){
        return new ServiceGenerator().createService(Endpoints.class);
    }
}