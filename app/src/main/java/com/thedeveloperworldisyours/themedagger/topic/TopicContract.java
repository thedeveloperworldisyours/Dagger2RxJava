package com.thedeveloperworldisyours.themedagger.topic;


import com.thedeveloperworldisyours.themedagger.BasePresenter;
import com.thedeveloperworldisyours.themedagger.BaseView;
import com.thedeveloperworldisyours.themedagger.data.Topics;

import java.util.List;

/**
 * Created by javierg on 19/04/2017.
 */

public interface TopicContract {

    interface Presenter extends BasePresenter {
        void fetch();
    }

    interface View extends BaseView<Presenter> {
        void showTopics(List<Topics> list);

        void showError();

        void setLoadingIndicator(boolean active);

    }
}
