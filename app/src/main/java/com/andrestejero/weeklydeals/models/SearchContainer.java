package com.andrestejero.weeklydeals.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchContainer {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    private List<Category> products;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @NonNull
    public List<Category> getProducts() {
        return CollectionUtils.safeList(products);
    }
}
