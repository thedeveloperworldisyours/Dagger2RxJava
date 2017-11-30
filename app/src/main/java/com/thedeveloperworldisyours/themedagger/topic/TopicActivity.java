package com.thedeveloperworldisyours.themedagger.topic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.themedagger.DiscernApplication;
import com.thedeveloperworldisyours.themedagger.R;
import com.thedeveloperworldisyours.themedagger.data.RemoteDataSource;
import com.thedeveloperworldisyours.themedagger.utils.schedulers.BaseSchedulerProvider;


import javax.inject.Inject;

import static com.thedeveloperworldisyours.themedagger.utils.Utils.addFragmentToActivity;

public class TopicActivity extends AppCompatActivity {

    @Inject
    RemoteDataSource mRemoteDataSource;

    @Inject
    BaseSchedulerProvider mSchedulerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_activity);
        initializeDagger();
        initFragment();
    }

    private void initializeDagger() {
        DiscernApplication app = (DiscernApplication) getApplication();
        app.getAppComponent().inject(this);
    }

    private void initFragment () {
        TopicFragment themeFragment = (TopicFragment) getSupportFragmentManager().
                findFragmentById(R.id.theme_activity_contentFrame);
        if (themeFragment == null) {
            themeFragment = themeFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(),
                    themeFragment, R.id.theme_activity_contentFrame);
        }
        new TopicPresenter(mRemoteDataSource, themeFragment, mSchedulerProvider);

    }


}
