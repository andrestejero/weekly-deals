package com.andrestejero.weeklydeals.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        return new DecimalFormat("'$'#,###.##", symbols).format(price);
    }

    @NonNull
    public static String gamePercent(@NonNull BigDecimal percent) {
        return new DecimalFormat("'SAVE' ##'%'").format(percent);
    }

}
