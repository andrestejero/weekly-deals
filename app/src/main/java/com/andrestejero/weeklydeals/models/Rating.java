package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import java.math.BigDecimal;

public class Rating {

    @Nullable
    private Float average;

    @Nullable
    private Integer total;

    @Nullable
    public Float getAverage() {
        return average;
    }

    @Nullable
    public Integer getTotal() {
        return total;
    }
}
