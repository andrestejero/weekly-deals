package com.andrestejero.weeklydeals.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionUtils {

    private CollectionUtils() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    public static <E> boolean isNullOrEmpty(@Nullable E[] array) {
        return array == null || array.length == 0;
    }

    public static <E> boolean isNullOrEmpty(@Nullable Collection<E> collection) {
        return collection == null || collection.size() == 0;
    }

    public static <E> boolean isNotEmpty(@Nullable Collection<E> collection) {
        return !isNullOrEmpty(collection);
    }

    @NonNull
    public static <E> Iterable<E> safeIterable(@Nullable Collection<E> collection) {
        if (collection != null) {
            return collection;
        }
        return Collections.emptyList();
    }

    @NonNull
    public static <E> List<E> safeList(@Nullable List<E> collection) {
        if (collection != null) {
            return collection;
        }
        return Collections.emptyList();
    }

    public static <E> int safeSize(@Nullable Collection<E> collection) {
        if (collection != null) {
            return collection.size();
        }
        return 0;
    }

}
