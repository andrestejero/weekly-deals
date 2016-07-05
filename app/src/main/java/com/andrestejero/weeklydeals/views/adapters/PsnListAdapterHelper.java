package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.Product;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.StringUtils;

public class PsnListAdapterHelper {

    private final static int CATEGORY_IMAGE_WIDTH = 50;
    private final static int PRODUCT_IMAGE_WIDTH = 100;

    private PsnListAdapterHelper() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    public static void showCategoryName(@NonNull Category category, @NonNull TextView categoryName) {
        categoryName.setText(category.getName());
    }

    public static void showCategoryImage(@NonNull Context context, @NonNull Category category, @NonNull ImageView categoryImage) {
        if (StringUtils.isNotEmpty(category.getImage())) {
            new ImageRequest(context, category.getImage(), categoryImage).widthInPixels(CATEGORY_IMAGE_WIDTH, 1000).execute();
        } else {
            categoryImage.setImageResource(R.drawable.bg_image_placeholder_100dp);
        }
    }

    public static void showProductName(@NonNull Product product, @NonNull TextView productName) {
        productName.setText(product.getName());
    }

    public static void showProductImage(@NonNull Context context, @NonNull Product product, @NonNull ImageView productImage) {
        if (StringUtils.isNotEmpty(product.getImage())) {
            new ImageRequest(context, product.getImage(), productImage).widthInPixels(PRODUCT_IMAGE_WIDTH, 1000).execute();
        } else {
            productImage.setImageResource(R.drawable.bg_image_placeholder_100dp);
        }
    }

}
