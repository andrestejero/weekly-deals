package com.andrestejero.weeklydeals.views.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import com.andrestejero.weeklydeals.models.Target;
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

    @Nullable
    private OnItemClickListener mItemClickListener;

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
            BannerViewHolder viewHolder = (BannerViewHolder) holder;
            if (CollectionUtils.isNotEmpty(mBanners)) {
                viewHolder.bannersAdapter = new BannersAdapter(mContext, mBanners);
                viewHolder.bannersPager.setAdapter(viewHolder.bannersAdapter);
                viewHolder.bannersPager.setVisibility(View.VISIBLE);
                viewHolder.tabLayout.setVisibility(View.VISIBLE);
                viewHolder.tabLayout.setupWithViewPager(viewHolder.bannersPager, true);
                viewHolder.bannersAdapter.setOnBannerClickListener(new BannersAdapter.OnBannerClickListener() {
                    @Override
                    public void onBannerClick(@NonNull Target target) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onBannerClick(target);
                        }
                    }
                });
            } else {
                viewHolder.bannersPager.setVisibility(View.GONE);
                viewHolder.tabLayout.setVisibility(View.GONE);
            }
        }
        if (type == TYPE_ITEM) {
            if (CollectionUtils.isNotEmpty(mCategories)) {
                CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
                final Category category = mCategories.get(position - 1);
                PsnListAdapterHelper.showCategoryName(category, viewHolder.categoryName);
                PsnListAdapterHelper.showCategoryImage(mContext, category, viewHolder.categoryImage);
                viewHolder.actionableCover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onCategoryClick(category);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.safeSize(mCategories) + 1;
    }

    public interface OnItemClickListener {
        void onCategoryClick(@NonNull Category category);
        void onBannerClick(@NonNull Target target);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {
        private final ViewPager bannersPager;
        private BannersAdapter bannersAdapter;
        private final TabLayout tabLayout;

        private BannerViewHolder(View itemView) {
            super(itemView);
            bannersPager = (ViewPager) itemView.findViewById(R.id.banners);
            tabLayout = (TabLayout) itemView.findViewById(R.id.tabLayout);
        }
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        private View categoryContent;
        private View separator;
        private ImageView categoryImage;
        private TextView categoryName;
        private View actionableCover;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            categoryContent = itemView.findViewById(R.id.rlContent);
            separator = itemView.findViewById(R.id.separator);
            categoryImage = (ImageView) itemView.findViewById(R.id.ivCategoryImage);
            categoryName = (TextView) itemView.findViewById(R.id.tvCategoryName);
            actionableCover = itemView.findViewById(R.id.actionableCover);
        }
    }

}
