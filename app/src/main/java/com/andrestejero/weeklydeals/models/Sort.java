package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

public class Sort {

    @Nullable
    private String id;

    @Nullable
    private String name;

    private boolean selected;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
