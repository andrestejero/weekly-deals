package com.andrestejero.weeklydeals.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.List;

public class HomeContainer {

    @Nullable
    private List<Banner> banners;

    @NonNull
    public List<Banner> getBanners() {
        return CollectionUtils.safeList(banners);
    }
}
