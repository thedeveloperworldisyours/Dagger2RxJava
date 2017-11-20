package com.thedeveloperworldisyours.themedagger;

import com.thedeveloperworldisyours.themedagger.data.RemoteDataSource;
import com.thedeveloperworldisyours.themedagger.data.Service;
import com.thedeveloperworldisyours.themedagger.data.Topics;
import com.thedeveloperworldisyours.themedagger.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.themedagger.schedulers.ImmediateSchedulerProvider;
import com.thedeveloperworldisyours.themedagger.theme.ThemeContract;
import com.thedeveloperworldisyours.themedagger.theme.ThemePresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static io.reactivex.Maybe.error;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 10/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThemePresenterTest {

    @Mock
    private RemoteDataSource mRemoteDataSource;

    @Mock
    private ThemeContract.View mView;

    private BaseSchedulerProvider mSchedulerProvider;

    ThemePresenter mThemePresenter;

    List<Topics> mList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Topics topics = new Topics(1, "Discern The Beach");
        Topics topicsTwo = new Topics(2, "Discern The Football Player");
        mList = new ArrayList<>();
        mList.add(topics);
        mList.add(topicsTwo);

        mSchedulerProvider = new ImmediateSchedulerProvider();
        mThemePresenter = new ThemePresenter(mRemoteDataSource, mView, mSchedulerProvider);


    }

    @Test
    public void fetchData() {

        when(mRemoteDataSource.getTopicsRx())
                .thenReturn(rx.Observable.just(mList));

        mThemePresenter.fetch();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(false);
        inOrder.verify(mView).showTopics(mList);

    }

    @Test
    public void fetchError() {

        Exception exception = new Exception();
        when(mRemoteDataSource.getTopicsRx())
                .thenReturn(Observable.error(exception));
        mThemePresenter.fetch();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showError();
        verify(mView, never()).showTopics(anyList());
    }

}
