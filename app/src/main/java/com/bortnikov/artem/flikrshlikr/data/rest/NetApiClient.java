package com.bortnikov.artem.flikrshlikr.data.rest;

import com.bortnikov.artem.flikrshlikr.data.Endpoints;
import com.bortnikov.artem.flikrshlikr.data.model.retrofit.FeedList;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetApiClient {
    private static final String METHOD_RECENT = "flickr.photos.getRecent";
    private static final String METHOD_SEARCH = "flickr.photos.search";
    private static final String API_KEY = "3f3f9b8120d3870dabda80525c1686c5";
    private static final String FORMAT = "json";
    private static final String NOJSONCALLBACK = "1";
    private static final String EXTRAS = "url_s";

    private static final NetApiClient ourInstance = new NetApiClient();

    public static NetApiClient getInstance() {
        return ourInstance;
    }

    private Endpoints netApi = new ServiceGenerator().createService(Endpoints.class);

    private NetApiClient() {
    }

    public Flowable<FeedList> getFeed() {
        return netApi.getFeedResponse(METHOD_RECENT, API_KEY, EXTRAS, FORMAT, NOJSONCALLBACK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    public Flowable<List<SearchList>> getSearch(String user) {
//        return netApi.getSearchLine(user)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
}