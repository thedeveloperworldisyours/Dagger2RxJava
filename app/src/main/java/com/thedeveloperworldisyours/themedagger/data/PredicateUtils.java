package com.thedeveloperworldisyours.themedagger.data;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by javierg on 08/05/2017.
 */

public class PredicateUtils {

    public static <T> Predicate<T> check(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return true;
        };
    }

}
