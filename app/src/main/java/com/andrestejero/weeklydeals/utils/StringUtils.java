package com.andrestejero.weeklydeals.utils;

import android.support.annotation.Nullable;

public class StringUtils {

    private StringUtils() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    public static boolean isEmpty(@Nullable CharSequence string) {
        return string == null || string.toString().trim().isEmpty();
    }

    public static boolean isNotEmpty(@Nullable CharSequence string) {
        return !isEmpty(string);
    }

}
