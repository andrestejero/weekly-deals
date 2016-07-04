package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameList {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    @SerializedName("products")
    private List<Game> games;

    @Nullable
    @SerializedName("lists")
    private List<Category> categories;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public List<Game> getGames() {
        return CollectionUtils.safeList(games);
    }

    @Nullable
    public List<Category> getCategories() {
        return CollectionUtils.safeList(categories);
    }
}