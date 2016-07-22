package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

public class FilterApplied {

    @Nullable
    private String id;

    @Nullable
    private String value;

    public FilterApplied() {
    }

    public FilterApplied(@Nullable String id, @Nullable String value) {
        this.id = id;
        this.value = value;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getValue() {
        return value;
    }
}
