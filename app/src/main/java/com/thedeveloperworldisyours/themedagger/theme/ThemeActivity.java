package com.thedeveloperworldisyours.themedagger.theme;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.themedagger.DiscernApplication;
import com.thedeveloperworldisyours.themedagger.R;
import com.thedeveloperworldisyours.themedagger.data.RemoteDataSource;
import com.thedeveloperworldisyours.themedagger.schedulers.BaseSchedulerProvider;


import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class ThemeActivity extends AppCompatActivity {

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
        ThemeFragment themeFragment = (ThemeFragment) getSupportFragmentManager().
                findFragmentById(R.id.theme_activity_contentFrame);
        if (themeFragment == null) {
            themeFragment = themeFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(),
                    themeFragment, R.id.theme_activity_contentFrame);
        }
        new ThemePresenter(mRemoteDataSource, themeFragment, mSchedulerProvider);

    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
