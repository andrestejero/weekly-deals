package com.andrestejero.weeklydeals.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    private static final String LOG_TAG = DateUtils.class.toString();
    private static final Locale LOCALE_AR = new Locale("es", "AR");
    private static final Locale LOCALE_US = new Locale("en", "US");
    public static final String TIMEZONE_AR = "America/Argentina/Buenos_Aires";
    private static final TimeZone SERVER_TIME_ZONE = TimeZone.getTimeZone("GMT");

    private DateUtils() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    @NonNull
    public static String dateFormat(@NonNull Date date, @NonNull String format) {
        return dateFormat(date, format, TimeZone.getTimeZone(TIMEZONE_AR));
    }

    @NonNull
    public static String dateFormat(@NonNull Date date, @NonNull String format, @NonNull TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, LOCALE_US);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(date);
    }

    public static int getDaysDifference(long fromDate, long toDate) {
        return (int) ((toDate - fromDate) / (1000 * 60 * 60 * 24));
    }

    @NonNull
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

    @Nullable
    public static Date stringFormat(@Nullable String date, @NonNull String format) {
        return stringFormat(date, format, null);
    }

    @Nullable
    public static Date stringFormat(@Nullable String date, @NonNull String format, @Nullable TimeZone timeZone) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, LOCALE_US);
            if (timeZone != null) {
                simpleDateFormat.setTimeZone(timeZone);
            }
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Date (" + StringUtils.safeString(date) + ") could not be parsed with format: " + format);
        }
        return null;
    }

    @NonNull
    public static String serverDateFormat(@NonNull Date date, @NonNull String format) {
        return dateFormat(date, format, SERVER_TIME_ZONE);
    }

    @Nullable
    public static Date serverStringFormat(@Nullable String date, @NonNull String format) {
        return stringFormat(date, format, SERVER_TIME_ZONE);
    }

}
