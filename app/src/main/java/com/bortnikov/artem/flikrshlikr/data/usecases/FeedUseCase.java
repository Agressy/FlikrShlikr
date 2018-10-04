package com.bortnikov.artem.flikrshlikr.data.usecases;

import com.bortnikov.artem.flikrshlikr.data.Endpoints;
import com.bortnikov.artem.flikrshlikr.data.model.realm.RealmModel;
import com.bortnikov.artem.flikrshlikr.data.model.retrofit.FeedList;
import com.bortnikov.artem.flikrshlikr.data.model.retrofit.Photo;
import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class FeedUseCase {
    private static final String METHOD_RECENT = "flickr.photos.getRecent";
    private static final String METHOD_SEARCH = "flickr.photos.search";
    private static final String API_KEY = "3f3f9b8120d3870dabda80525c1686c5";
    private static final String FORMAT = "json";
    private static final String NOJSONCALLBACK = "1";
    private static final String EXTRAS = "url_s";

    private Endpoints endpoints;

    public FeedUseCase(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public Flowable<Integer> getFeed() {
        return endpoints
                .getFeedResponse(METHOD_RECENT, API_KEY, EXTRAS, FORMAT, NOJSONCALLBACK)
                .map(feedList -> {
                    Realm realm = Realm.getDefaultInstance();
                    List<Photo> feed = feedList.getPhotos().getPhoto();
                    realm.executeTransactionAsync((Realm realm1) -> {
                        int i = 0;
                        for (Photo p : feed) {
                            RealmModel realmItem = new RealmModel();
                            realmItem.id = i;
                            realmItem.title = (String.valueOf(p.getTitle()));
                            realmItem.imageUrl = (String.valueOf(p.getUrlS()));
                            realm1.copyToRealmOrUpdate(realmItem);
                            i++;
                        }
                    });
                    realm.close();
                    return feed.size();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<ArrayList<DataViewModel>> getSearch(String tag) {
        return endpoints.getSearchResponse(METHOD_SEARCH, API_KEY, tag, EXTRAS, FORMAT, NOJSONCALLBACK)
                .map(feedList -> {
                    List<Photo> feed = feedList.getPhotos().getPhoto();
                    ArrayList<DataViewModel> result = new ArrayList<>();
                    int i = 0;
                    for (Photo p : feed) {
                        DataViewModel dataItem = new DataViewModel(i,
                                String.valueOf(p.getTitle()),
                                String.valueOf(p.getUrlS()));
                        result.add(dataItem);
                        i++;
                    }
                    return result;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
