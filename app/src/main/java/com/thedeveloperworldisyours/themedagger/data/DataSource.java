package com.thedeveloperworldisyours.themedagger.data;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by javierg on 19/04/2017.
 */

public interface DataSource {

    Retrofit getService();

    OkHttpClient getOkHttpClient();

}
