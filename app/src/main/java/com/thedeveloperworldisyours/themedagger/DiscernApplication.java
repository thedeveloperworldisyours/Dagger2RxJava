package com.thedeveloperworldisyours.themedagger;

import android.app.Application;

/**
 * Created by javierg on 20/04/2017.
 */

public class DiscernApplication extends Application {

    AppModule mAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppModule = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppModule getAppComponent() {
        return mAppModule;
    }

}
