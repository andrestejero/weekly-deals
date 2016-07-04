package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

public class Category {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    private String image;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getImage() {
        return image;
    }
}
