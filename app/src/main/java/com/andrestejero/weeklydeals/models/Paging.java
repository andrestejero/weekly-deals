package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

public class Paging {

    @Nullable
    private Integer offset;

    @Nullable
    private Integer limit;

    @Nullable
    private Integer total;

    @Nullable
    public Integer getOffset() {
        return offset;
    }

    @Nullable
    public Integer getLimit() {
        return limit;
    }

    @Nullable
    public Integer getTotal() {
        return total;
    }

}
