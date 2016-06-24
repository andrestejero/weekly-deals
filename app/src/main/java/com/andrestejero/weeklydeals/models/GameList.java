package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.List;

public class GameList {

    @Nullable
    private String id;

    @Nullable
    private List<Game> games;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public List<Game> getGames() {
        return CollectionUtils.safeList(games);
    }
}
