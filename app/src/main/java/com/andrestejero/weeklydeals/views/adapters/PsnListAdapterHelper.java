package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.StringUtils;

public class PsnListAdapterHelper {

    private PsnListAdapterHelper() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    public static void showCategoryName(@NonNull Category category, @NonNull TextView categoryName) {
        categoryName.setText(category.getName());
    }

    public static void showCategoryImage(@NonNull Context context, @NonNull Category category, @NonNull ImageView categoryImage) {
        if (StringUtils.isNotEmpty(category.getImage())) {
            new ImageRequest(context, category.getImage(), categoryImage).widthInPixels(50, 1000).execute();
        } else {
            categoryImage.setImageResource(R.drawable.bg_image_placeholder_100dp);
        }
    }

}
