package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Price {

    @Nullable
    private BigDecimal normal;

    @Nullable
    private BigDecimal discount;

    @Nullable
    @SerializedName("discount_price")
    private BigDecimal discountPrice;

    @Nullable
    @SerializedName("bonus_discount")
    private BigDecimal bonusDiscount;

    @Nullable
    @SerializedName("bonus_discount_price")
    private BigDecimal bonusDiscountPrice;

    @Nullable
    public BigDecimal getNormal() {
        return normal;
    }

    @Nullable
    public BigDecimal getDiscount() {
        return discount;
    }

    @Nullable
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    @Nullable
    public BigDecimal getBonusDiscount() {
        return bonusDiscount;
    }

    @Nullable
    public BigDecimal getBonusDiscountPrice() {
        return bonusDiscountPrice;
    }
}
