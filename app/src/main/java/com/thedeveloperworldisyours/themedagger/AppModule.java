package com.thedeveloperworldisyours.themedagger;

import com.thedeveloperworldisyours.themedagger.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.themedagger.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

}
