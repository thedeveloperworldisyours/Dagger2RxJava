package com.thedeveloperworldisyours.themedagger;


import com.google.gson.Gson;
import com.thedeveloperworldisyours.themedagger.data.RemoteDataSource;
import com.thedeveloperworldisyours.themedagger.data.Topics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

/**
 * Created by javierg on 05/05/2017.
 */

public class RemoteDataSourceTest {

    List<Topics> mResultList;
    MockWebServer mMockWebServer;
    TestSubscriber<List<Topics>> mSubscriber;

    @Before
    public void setUp() {
        Topics topics = new Topics(1, "Discern The Beach");
        Topics topicsTwo = new Topics(2, "Discern The Football Player");
        mResultList = new ArrayList();
        mResultList.add(topics);
        mResultList.add(topicsTwo);

        mMockWebServer = new MockWebServer();
        mSubscriber = new TestSubscriber<>();
    }

    @Test
    public void serverCallWithError() {
        //Given
        String url = "dfdf/";
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mResultList)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();
        RemoteDataSource remoteDataSource = new RemoteDataSource(retrofit);

        //When
        remoteDataSource.getTopicsRx().subscribe(mSubscriber);

        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertCompleted();
    }

    @Test
    public void severCallWithSuccessful() {
        //Given
        String url = "https://guessthebeach.herokuapp.com/api/";
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mResultList)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();
        RemoteDataSource remoteDataSource = new RemoteDataSource(retrofit);

        //When
        remoteDataSource.getTopicsRx().subscribe(mSubscriber);

        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertCompleted();
    }

}
