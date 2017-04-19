package com.thedeveloperworldisyours.themedagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.themedagger.data.Repository;
import com.thedeveloperworldisyours.themedagger.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.themedagger.schedulers.SchedulerProvider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 19/04/2017.
 */

public class Injection {

    public static Repository provideRepository(@NonNull Context context) {
        checkNotNull(context);
        return Repository.getInstance();
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

}
