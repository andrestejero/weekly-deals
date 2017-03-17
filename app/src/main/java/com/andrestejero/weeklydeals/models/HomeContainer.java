package com.andrestejero.weeklydeals.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeContainer {

    @Nullable
    private List<Banner> banners;

    @Nullable
    @SerializedName("lists")
    private List<Category> categories;

    @NonNull
    public List<Banner> getBanners() {
        return CollectionUtils.safeList(banners);
    }

    @NonNull
    public List<Category> getCategories() {
        return CollectionUtils.safeList(categories);
    }
}
