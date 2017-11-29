package com.thedeveloperworldisyours.themedagger.data;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by javierg on 19/04/2017.
 */

public interface Service {

    @GET("topics/")
    Observable<List<Topics>> getTopicsRx();

}

