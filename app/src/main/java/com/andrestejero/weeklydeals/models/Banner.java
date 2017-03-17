package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

public class Banner {

    @Nullable
    private String name;

    @Nullable
    private Target target;

    @Nullable
    private String image;

    @Nullable
    private Integer width;

    @Nullable
    private Integer height;

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public Target getTarget() {
        return target;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    @Nullable
    public Integer getWidth() {
        return width;
    }

    @Nullable
    public Integer getHeight() {
        return height;
    }
}
