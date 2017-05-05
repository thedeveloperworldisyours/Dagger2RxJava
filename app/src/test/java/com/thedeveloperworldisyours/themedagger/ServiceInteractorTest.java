package com.thedeveloperworldisyours.themedagger;

import android.util.LruCache;

import com.google.gson.Gson;
import com.thedeveloperworldisyours.themedagger.data.ServiceInteractor;
import com.thedeveloperworldisyours.themedagger.data.Topics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.asm.tree.InsnList.check;

/**
 * Created by javierg on 05/05/2017.
 */

public class ServiceInteractorTest {

    @Mock
    private LruCache<String, List<Topics>> mCache;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);
        when(mCache.get(anyString())).thenReturn(null);
    }

    @Test
    public void mockService() {

        Topics topics = new Topics(1, "football");
        Topics topicSecond = new Topics(2, "Beaches");
        List<Topics> result = new ArrayList();

        result.add(topics);
        result.add(topicSecond);

        MockWebServer mockService = new MockWebServer();
        mockService.enqueue(new MockResponse().setBody(new Gson().toJson(result)));

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockService.url("dfdf/"))
                .build();

        TestSubscriber<List<Topics>> subscriber = new TestSubscriber<>();
        ServiceInteractor serviceInteractor = new ServiceInteractor(retrofit, mCache);
        serviceInteractor.searchUsers().subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }


}
