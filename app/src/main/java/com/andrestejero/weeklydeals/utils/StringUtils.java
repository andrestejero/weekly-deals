package com.andrestejero.weeklydeals.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
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

    @NonNull
    public static String safeString(@Nullable String string) {
        return string != null ? string : "";
    }

    @NonNull
    public static String safeTrim(@Nullable String string) {
        return safeString(string).trim();
    }

    @NonNull
    public static <E> String join(@NonNull E[] array, @NonNull String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            E e = array[i];
            String description = e.toString();
            sb.append(description);
            if (i < array.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    @NonNull
    public static <E> String join(@NonNull Collection<E> collection, @NonNull String separator) {
        return join(collection.toArray(), separator);
    }

    public static String replaceSpaces(@NonNull String string) {
        return string.replaceAll(" ", "_").toLowerCase();
    }
}
