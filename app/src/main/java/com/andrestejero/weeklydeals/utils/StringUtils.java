package com.andrestejero.weeklydeals.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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

    @NonNull
    public static String gamePrice(@NonNull BigDecimal price) {
        return new DecimalFormat("'U$S' #0.00").format(price);
    }

    @NonNull
    public static String gamePercent(@NonNull BigDecimal percent) {
        return new DecimalFormat("'SAVE' ##'%'").format(percent);
    }

}
