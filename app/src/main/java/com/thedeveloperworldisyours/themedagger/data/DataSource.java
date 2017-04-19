package com.thedeveloperworldisyours.themedagger.data;

import java.util.List;

import okhttp3.OkHttpClient;
import rx.Observable;

/**
 * Created by javierg on 19/04/2017.
 */

public interface DataSource {

    Service getService();

    OkHttpClient getOkHttpClient();

    Observable<List<Topics>> getTopicsRx();
}
