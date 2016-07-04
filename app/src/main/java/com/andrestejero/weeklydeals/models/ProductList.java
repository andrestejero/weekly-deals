package com.andrestejero.weeklydeals.models;

import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList {

    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    @SerializedName("products")
    private List<Product> products;

    @Nullable
    @SerializedName("lists")
    private List<Category> categories;

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public List<Product> getProducts() {
        return CollectionUtils.safeList(products);
    }

    @Nullable
    public List<Category> getCategories() {
        return CollectionUtils.safeList(categories);
    }
}