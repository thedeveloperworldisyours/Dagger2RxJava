package com.thedeveloperworldisyours.themedagger;

import com.thedeveloperworldisyours.themedagger.data.RemoteDataSource;
import com.thedeveloperworldisyours.themedagger.utils.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.themedagger.utils.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thedeveloperworldisyours.themedagger.utils.Constants.URL_BASE;

/**
 * Created by javierg on 20/04/2017.
 */
@Module
public class AppModule {

    DiscernApplication mDiscernApplication;

    public AppModule(DiscernApplication discernApplication) {
        mDiscernApplication = discernApplication;
    }

    @Singleton
    @Provides
    RemoteDataSource provideRemoteDataSource() {return new RemoteDataSource( new Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build());}

    @Singleton
    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

}
