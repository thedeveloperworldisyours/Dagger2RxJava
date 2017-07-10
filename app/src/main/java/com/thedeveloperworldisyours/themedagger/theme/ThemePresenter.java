package com.thedeveloperworldisyours.themedagger.theme;

import android.support.annotation.NonNull;
import android.util.LruCache;

import com.thedeveloperworldisyours.themedagger.data.Repository;
import com.thedeveloperworldisyours.themedagger.data.ServiceInteractor;
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

    private Repository mRepository;

    private ThemeContract.View mView;

    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;


    public ThemePresenter(@NonNull Repository repository, @NonNull ThemeContract.View view, @NonNull BaseSchedulerProvider provider) {
        this.mRepository = checkNotNull(repository, "repository cannot be null");
        this.mView = checkNotNull(view, "view cannot be null!");
        this.mSchedulerProvider = checkNotNull(provider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();

        mView.setPresenter(this);
    }

    @Override
    public void fetch() {

        LruCache<String, List<Topics>> cache = new LruCache<>(5 * 1024 * 1024); // 5MiB

        final ServiceInteractor interactor = new ServiceInteractor(mRepository.getService(), cache);

        Subscription subscription = interactor.getTopics()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe((List<Topics> listTopics) -> {
                            mView.setLoadingIndicator(false);
                            mView.showTopics(listTopics);
                        },
                        (Throwable error) -> {
                            try {
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
