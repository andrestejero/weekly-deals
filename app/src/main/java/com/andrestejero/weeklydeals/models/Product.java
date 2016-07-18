package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    private String image;

    @Nullable
    @SerializedName("game_content_type")
    private String gameContentType;

    @Nullable
    private List<String> platforms;

    @Nullable
    private Price price;

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

    @Nullable
    public String getGameContentType() {
        return gameContentType;
    }

    @Nullable
    public List<String> getPlatforms() {
        return CollectionUtils.safeList(platforms);
    }

    @Nullable
    public Price getPrice() {
        return price;
    }
}
