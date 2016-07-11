package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

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
    @SerializedName("start_date")
    private Date startDate;

    @Nullable
    @SerializedName("end_date")
    private Date endDate;

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

    @Nullable
    public Date getStartDate() {
        return startDate;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }
}
