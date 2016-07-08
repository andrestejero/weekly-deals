package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ProductDetail {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    private String image;

    @Nullable
    private List<String> platforms;

    @Nullable
    private Price price;

    @Nullable
    @SerializedName("media_resources")
    private List<MediaResource> mediaResources;

    @Nullable
    private Rating rating;

    @Nullable
    @SerializedName("game_content_type")
    private String gameContentType;

    @Nullable
    @SerializedName("release_date")
    private Date releaseDate;

    @Nullable
    private String provider;

    @Nullable
    private String description;

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
    public List<String> getPlatforms() {
        return CollectionUtils.safeList(platforms);
    }

    @Nullable
    public Price getPrice() {
        return price;
    }

    @Nullable
    public List<MediaResource> getMediaResources() {
        return CollectionUtils.safeList(mediaResources);
    }

    @Nullable
    public Rating getRating() {
        return rating;
    }

    @Nullable
    public String getGameContentType() {
        return gameContentType;
    }

    @Nullable
    public Date getReleaseDate() {
        return releaseDate;
    }

    @Nullable
    public String getProvider() {
        return provider;
    }

    @Nullable
    public String getDescription() {
        return description;
    }
}
