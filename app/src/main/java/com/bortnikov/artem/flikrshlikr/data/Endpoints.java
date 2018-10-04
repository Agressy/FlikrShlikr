package com.bortnikov.artem.flikrshlikr.data;

import com.bortnikov.artem.flikrshlikr.data.model.retrofit.FeedList;

import io.reactivex.Flowable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("rest/")
    Flowable<FeedList> getFeedResponse(@Query("method") String method,
                                       @Query("api_key") String apiKey,
                                       @Query("extras") String extras,
                                       @Query("format") String format,
                                       @Query("nojsoncallback") String nojsoncallback);

    @GET("rest/")
    Flowable<FeedList> getSearchResponse(@Query("method") String method,
                                         @Query("api_key") String apiKey,
                                         @Query("tags") String tags,
                                         @Query("extras") String extras,
                                         @Query("format") String format,
                                         @Query("nojsoncallback") String nojsoncallback);

}
