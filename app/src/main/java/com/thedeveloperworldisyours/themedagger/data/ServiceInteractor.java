package com.thedeveloperworldisyours.themedagger.data;

import android.util.LruCache;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by javierg on 04/05/2017.
 */

public class ServiceInteractor {
    private LruCache<String, List<Topics>> cache;
    private Service service;

    public ServiceInteractor(Retrofit retrofit, LruCache<String, List<Topics>> cache) {
        this.cache = cache;
        this.service = retrofit.create(Service.class);
    }

    public Observable<List<Topics>> searchUsers() {
        return Observable.concat(cachedResults(), networkResults()).first();
    }

    private Observable<List<Topics>> cachedResults() {
        return Observable.just(cache.get("query"))
                .filter((List<Topics> result) ->
                        result != null
                );
    }

    private Observable<List<Topics>> networkResults() {
        return service.getTopicsRx()
                .doOnNext((List<Topics> result) ->
                        cache.put("query", result));
    }
}
