package com.thedeveloperworldisyours.themedagger.data;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by javierg on 19/04/2017.
 */

public class Repository implements DataSource {

    public static final int TIMER_HTTP_OK = 5;
    @Nullable
    private static Repository sInstance = null;

//    public static synchronized Repository getInstance() {
//        if (sInstance == null) {
//            sInstance = new Repository();
//        }
//        return sInstance;
//    }

    @Override
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(TIMER_HTTP_OK, TimeUnit.SECONDS)
                .readTimeout(TIMER_HTTP_OK, TimeUnit.SECONDS)
                .writeTimeout(TIMER_HTTP_OK, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public Service getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Service.URL_BASE)
                .client(getOkHttpClient())
                .build();

        return retrofit.create(Service.class);
    }

    @Override
    public Observable<List<Topics>> getTopicsRx() {

        return getService().getTopicsRx();
    }



}