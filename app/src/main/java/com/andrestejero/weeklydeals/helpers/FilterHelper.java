package com.andrestejero.weeklydeals.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.models.FilterApplied;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterHelper {

    private FilterHelper() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    @NonNull
    public static Map<String, String> getSelectedFilters(@Nullable List<FilterApplied> filtersApplied) {
        Map<String, String> filters = new HashMap<>();
        for (FilterApplied filterApplied : CollectionUtils.safeIterable(filtersApplied)) {
            filters.put(filterApplied.getId(), filterApplied.getValue());
        }
        return filters;
    }


}
