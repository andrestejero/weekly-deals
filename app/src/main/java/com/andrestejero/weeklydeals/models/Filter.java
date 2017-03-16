package com.andrestejero.weeklydeals.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Filter {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    private List<Value> values;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @NonNull
    public List<Value> getValues() {
        return CollectionUtils.safeList(values);
    }

    @NonNull
    public List<Value> getSelectedOptions() {
        List<Value> values = getValues();
        if (CollectionUtils.isNullOrEmpty(values)) {
            return Collections.emptyList();
        }

        List<Value> selectedOptions = new ArrayList<>();
        for (Value value : values) {
            if (value.isSelected()) {
                selectedOptions.add(value);
            }
        }
        return selectedOptions;
    }

    public void toggleOption(@NonNull Value value) {
        value.setSelected(!value.isSelected());
    }
}
