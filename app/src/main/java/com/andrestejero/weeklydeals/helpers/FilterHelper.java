package com.andrestejero.weeklydeals.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.models.Filter;
import com.andrestejero.weeklydeals.models.Value;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterHelper {

    private FilterHelper() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    @NonNull
    public static Map<String, String> getSelectedFilters(@Nullable List<Filter> filterList) {
        Map<String, String> filters = new HashMap<>();
        for (Filter filter : CollectionUtils.safeIterable(filterList)) {
            if (StringUtils.isNotEmpty(filter.getId())) {
                StringBuilder sb = new StringBuilder();
                for (Value option : filter.getSelectedOptions()) {
                    if (StringUtils.isNotEmpty(option.getId())) {
                        if (StringUtils.isNotEmpty(sb)) {
                            sb.append(",");
                        }
                        sb.append(option.getId());
                    }
                }
                if (StringUtils.isNotEmpty(sb)) {
                    filters.put(filter.getId(), sb.toString());
                }
            }
        }
        return filters;
    }
}
