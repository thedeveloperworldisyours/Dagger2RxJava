package com.thedeveloperworldisyours.themedagger;

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

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 10/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThemePresenterTest {

    @Mock
    private Service mService;

    @Mock
    private Repository mRepository;

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
        mThemePresenter = new ThemePresenter(mRepository, mView, mSchedulerProvider);


    }

//    @Test
//    public void fetchAllTopics() {
//
//        doReturn(Observable.just(result))
//        .when(mRepository.getService());
//
//        verify(mView).showTopics(result);
//
//    }

    @Test
    public  void fetchData() {

        when(mService.getTopicsRx())
                .thenReturn(rx.Observable.just(mList));

        mThemePresenter.fetch();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView, times(1)).setLoadingIndicator(false);
        inOrder.verify(mView, times(1)).showTopics(anyList());



    }


//    @Test
//    public void fetchErrorShouldReturnErrorToView() {
//
//        Exception exception = new Exception();
//
//        when(charactersDataSource.getCharacters())
//                .thenReturn(rx.Observable.<CharactersResponseModel>error(exception));
//
//        MainPresenter mainPresenter = new MainPresenter(
//                this.charactersDataSource,
//                Schedulers.immediate(),
//                Schedulers.immediate(),
//                this.view
//        );
//
//        mainPresenter.loadData();
//
//        InOrder inOrder = Mockito.inOrder(view);
//        inOrder.verify(view, times(1)).onFetchDataStarted();
//        inOrder.verify(view, times(1)).onFetchDataError(exception);
//        verify(view, never()).onFetchDataCompleted();
//    }

}
