package com.bortnikov.artem.flikrshlikr.data;

import com.bortnikov.artem.flikrshlikr.data.model.FeedList;

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
//    @GET("/users/{user}")
//    Flowable<List<SearchModel>> getSearchLine(
//            @Path("user") String user);
}
