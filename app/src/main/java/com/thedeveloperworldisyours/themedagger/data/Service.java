package com.thedeveloperworldisyours.themedagger.data;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by javierg on 19/04/2017.
 */

public interface Service {
    String URL_BASE = "https://guessthebeach.herokuapp.com/api/";

    @GET("topics/")
    Observable<List<Topics>> getTopicsRx();

}

