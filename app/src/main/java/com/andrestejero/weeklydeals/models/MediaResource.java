package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class MediaResource {

    @Nullable
    private String type;

    @Nullable
    private String url;

    @Nullable
    @SerializedName("preview_url")
    private String previewUrl;

    @Nullable
    public String getType() {
        return type;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getPreviewUrl() {
        return previewUrl;
    }
}
