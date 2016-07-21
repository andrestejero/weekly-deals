package com.andrestejero.weeklydeals.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.List;

public class Filter {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    private List<Value> values;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @NonNull
    public List<Value> getValues() {
        return CollectionUtils.safeList(values);
    }
}
