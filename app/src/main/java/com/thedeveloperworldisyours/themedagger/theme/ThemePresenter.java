package com.thedeveloperworldisyours.themedagger.theme;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thedeveloperworldisyours.themedagger.data.RemoteDataSource;
import com.thedeveloperworldisyours.themedagger.data.Topics;
import com.thedeveloperworldisyours.themedagger.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 19/04/2017.
 */

public class ThemePresenter implements ThemeContract.Presenter {

    @NonNull
    private ThemeContract.View mView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private RemoteDataSource mRemoteDataSource;


    public ThemePresenter(@NonNull RemoteDataSource remoteDataSource, @NonNull ThemeContract.View view, @NonNull BaseSchedulerProvider provider) {
        this.mRemoteDataSource = checkNotNull(remoteDataSource, "remoteDataSource");
        this.mView = checkNotNull(view, "view cannot be null!");
        this.mSchedulerProvider = checkNotNull(provider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();

        mView.setPresenter(this);
    }

    @Override
    public void fetch() {

        Subscription subscription = mRemoteDataSource.getTopicsRx()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe((List<Topics> listTopics) -> {
                            mView.setLoadingIndicator(false);
                            mView.showTopics(listTopics);
                        },
                        (Throwable error) -> {
                            try {
                                Log.d("Throwable", error.toString());
                                mView.showError();
                            } catch (Throwable t) {
                                throw new IllegalThreadStateException();
                            }

                        },
                        () -> {
                        });

        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        fetch();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

}
