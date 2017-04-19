package com.thedeveloperworldisyours.themedagger.theme;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 19/04/2017.
 */
@Module
public class ThemeComponent {

    private final ThemeContract.View mView;

    public ThemeComponent() {
        mView = null;
    }

    @Provides
    ThemeContract.View provideThemeContractView() {
        return mView;
    }
}
