package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Game {

    @Nullable
    private String description;

    @Nullable
    private BigDecimal price;

    @Nullable
    @SerializedName("discount-price")
    private BigDecimal discountPrice;

    @Nullable
    @SerializedName("plus-price")
    private BigDecimal plusPrice;

    @Nullable
    private String id;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public BigDecimal getPrice() {
        return price;
    }

    @Nullable
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    @Nullable
    public BigDecimal getPlusPrice() {
        return plusPrice;
    }
}
