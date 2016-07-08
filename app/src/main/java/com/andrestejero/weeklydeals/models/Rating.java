package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import java.math.BigDecimal;

public class Rating {

    @Nullable
    private BigDecimal average;

    @Nullable
    private BigDecimal total;

    @Nullable
    public BigDecimal getAverage() {
        return average;
    }

    @Nullable
    public BigDecimal getTotal() {
        return total;
    }
}
