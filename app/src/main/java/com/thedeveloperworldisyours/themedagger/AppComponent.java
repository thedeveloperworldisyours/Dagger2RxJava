package com.thedeveloperworldisyours.themedagger;

import com.thedeveloperworldisyours.themedagger.topic.TopicActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javierg on 20/04/2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(TopicActivity topicActivity);

}
