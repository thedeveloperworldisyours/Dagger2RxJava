package com.thedeveloperworldisyours.themedagger.data;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by javierg on 20/11/2017.
 */

public class RemoteDataSource implements Service {

    private Service api;

    public RemoteDataSource(Retrofit retrofit) {


        this.api = retrofit.create(Service.class);
    }


    @Override
    public Observable<List<Topics>> getTopicsRx() {
        return api.getTopicsRx();
    }
}
