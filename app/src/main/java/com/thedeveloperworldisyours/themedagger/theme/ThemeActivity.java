package com.thedeveloperworldisyours.themedagger.theme;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.themedagger.Injection;
import com.thedeveloperworldisyours.themedagger.R;


import static com.google.common.base.Preconditions.checkNotNull;

public class ThemeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_activity);

        initFragment();
    }


    private void initFragment () {
        ThemeFragment themeFragment = (ThemeFragment) getSupportFragmentManager().
                findFragmentById(R.id.theme_activity_contentFrame);
        if (themeFragment == null) {
            themeFragment = themeFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(),
                    themeFragment, R.id.theme_activity_contentFrame);
        }
        new ThemePresenter(Injection.provideRepository(this), themeFragment, Injection.provideSchedulerProvider());
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
