package com.thedeveloperworldisyours.themedagger;

import com.thedeveloperworldisyours.themedagger.data.Repository;
import com.thedeveloperworldisyours.themedagger.data.Topics;
import com.thedeveloperworldisyours.themedagger.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.themedagger.schedulers.ImmediateSchedulerProvider;
import com.thedeveloperworldisyours.themedagger.theme.ThemeContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 10/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThemePresenterTest {
    @Mock
    private Repository mRepository;

    @Mock
    private ThemeContract.View mView;

    private BaseSchedulerProvider mSchedulerProvider;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mSchedulerProvider = new ImmediateSchedulerProvider();
    }

    @Test
    public void fetchAllTopics() {
        Topics topics = new Topics(1, "Discern The Beach");
        Topics topicsTwo = new Topics(2, "Discern The Football Player");
        List<Topics> result = new ArrayList();
        result.add(topics);
        result.add(topicsTwo);

        doReturn(Observable.just(result))
        .when(mRepository.getService());

        verify(mView).showTopics(result);

    }
}
