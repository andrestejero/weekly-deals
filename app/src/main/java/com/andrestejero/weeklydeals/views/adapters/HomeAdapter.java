package com.andrestejero.weeklydeals.views.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Banner;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = HomeAdapter.class.getSimpleName();

    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ITEM = 1;

    @NonNull
    private Context mContext;

    @Nullable
    private final List<Banner> mBanners;

    @Nullable
    private List<Category> mCategories;

    public HomeAdapter(@NonNull Context context, @Nullable List<Banner> banners, @Nullable List<Category> categories) {
        this.mContext = context;
        this.mBanners = banners;
        this.mCategories = categories;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_BANNER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.banner_container, parent, false);
            return new BannerViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.psn_category_item, parent, false);
            return new CategoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_BANNER) {
            if (CollectionUtils.isNotEmpty(mBanners)) {
                BannerViewHolder viewHolder = (BannerViewHolder) holder;
                viewHolder.bannersAdapter = new BannersAdapter(mContext, mBanners);
                viewHolder.bannersPager.setAdapter(viewHolder.bannersAdapter);
            }
        }
        if (type == TYPE_ITEM) {
            if (CollectionUtils.isNotEmpty(mCategories)) {
                CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
                Category category = mCategories.get(position - 1);
                viewHolder.categoryName.setText(category.getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.safeSize(mCategories) + 1;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {
        private final ViewPager bannersPager;
        private BannersAdapter bannersAdapter;

        private BannerViewHolder(View itemView) {
            super(itemView);
            bannersPager = (ViewPager) itemView.findViewById(R.id.banners);
        }
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        private View categoryContent;
        private View separator;
        private ImageView categoryImage;
        private TextView categoryName;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            categoryContent = itemView.findViewById(R.id.rlContent);
            separator = itemView.findViewById(R.id.separator);
            categoryImage = (ImageView) itemView.findViewById(R.id.ivCategoryImage);
            categoryName = (TextView) itemView.findViewById(R.id.tvCategoryName);
        }
    }

}
