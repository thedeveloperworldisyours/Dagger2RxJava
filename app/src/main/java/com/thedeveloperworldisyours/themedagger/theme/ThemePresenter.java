package com.thedeveloperworldisyours.themedagger.theme;

import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.themedagger.data.RemoteDataSource;
import com.thedeveloperworldisyours.themedagger.data.Topics;
import com.thedeveloperworldisyours.themedagger.schedulers.BaseSchedulerProvider;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.thedeveloperworldisyours.themedagger.data.Service.URL_BASE;

/**
 * Created by javierg on 19/04/2017.
 */

public class ThemePresenter implements ThemeContract.Presenter {


    private ThemeContract.View mView;

    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;

    RemoteDataSource mRemoteDataSource;

    Retrofit mRetrofit;


    public ThemePresenter(@NonNull ThemeContract.View view, @NonNull BaseSchedulerProvider provider) {
        this.mView = checkNotNull(view, "view cannot be null!");
        this.mSchedulerProvider = checkNotNull(provider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mRemoteDataSource = new RemoteDataSource(mRetrofit);

        mView.setPresenter(this);
    }

    @Override
    public void fetch() {

//        LruCache<String, List<Topics>> cache = new LruCache<>(5 * 1024 * 1024); // 5MiB

//        final ServiceInteractor interactor = new ServiceInteractor(mRepository.getService(), cache);



        Subscription subscription = mRemoteDataSource.getTopicsRx()
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
