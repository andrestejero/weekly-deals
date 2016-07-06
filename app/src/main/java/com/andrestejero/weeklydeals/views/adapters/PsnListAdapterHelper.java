package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.Price;
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

    public static void showPlusBadge(@NonNull Price price, @NonNull ImageView productBadge) {
        if (price.getBonusDiscount() != null) {
            productBadge.setImageResource(R.drawable.ic_plus);
        }
    }

    public static void showNormalPrice(@NonNull Price price, @NonNull TextView normalPrice, @NonNull TextView discountPrice) {
        if (price.getNormal() != null && price.getDiscountPrice() != null) {
            normalPrice.setVisibility(View.VISIBLE);
            normalPrice.setText(StringUtils.gamePrice(price.getNormal()));
            normalPrice.setPaintFlags(normalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (price.getNormal() != null) {
            normalPrice.setVisibility(View.INVISIBLE);
            discountPrice.setText(StringUtils.gamePrice(price.getNormal()));
        }
    }

    public static void showDiscountPrice(@NonNull Price price, @NonNull TextView discountPrice) {
        if (price.getDiscountPrice() != null) {
            discountPrice.setText(StringUtils.gamePrice(price.getDiscountPrice()));
        }
    }

    public static void showPlusPrice(@NonNull Price price, @NonNull TextView plusPrice) {
        if (price.getBonusDiscountPrice() != null) {
            plusPrice.setVisibility(View.VISIBLE);
            plusPrice.setText(StringUtils.gamePrice(price.getBonusDiscountPrice()));
        } else {
            plusPrice.setVisibility(View.INVISIBLE);
        }
    }

    public static void updateDiscountContainer(@NonNull Price price, @NonNull TextView discount, @NonNull View discountContainer) {
        if (price.getDiscount() != null) {
            discountContainer.setVisibility(View.VISIBLE);
            discount.setText(StringUtils.gamePercent(price.getDiscount()));
        } else {
            discountContainer.setVisibility(View.INVISIBLE);
        }
    }

    public static void updateDiscountPlusContainer(@NonNull Price price, @NonNull TextView plusDiscount, @NonNull View discountPlusContainer) {
        if (price.getBonusDiscount() != null) {
            discountPlusContainer.setVisibility(View.VISIBLE);
            plusDiscount.setText(StringUtils.gamePercent(price.getBonusDiscount()));
        } else {
            discountPlusContainer.setVisibility(View.INVISIBLE);
        }
    }
}
