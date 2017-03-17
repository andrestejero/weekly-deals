package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Banner;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.List;

public class BannersAdapter extends PagerAdapter {

    private final static int BANNER_IMAGE_WIDTH = 200;

    @NonNull
    private final Context context;

    @NonNull
    private final List<Banner> banners;

    public BannersAdapter(@NonNull Context context, @NonNull List<Banner> banners) {
        this.context = context;
        this.banners = banners;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Banner banner = banners.get(position);
        ViewGroup layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.banner_item, container, false);
        container.addView(layout);
        showBanners(banner, layout);
        return layout;
    }

    private void showBanners(@NonNull Banner banner, @NonNull ViewGroup layout) {
        ImageView ivBanner = (ImageView) layout.findViewById(R.id.ivBanner);
        if (StringUtils.isNotEmpty(banner.getImage())) {
            new ImageRequest(context, banner.getImage(), ivBanner).widthInPixels(BANNER_IMAGE_WIDTH, 1000).execute();
        } else {
            ivBanner.setImageResource(R.drawable.bg_image_placeholder_200dp);
        }
    }

    @Override
    public int getCount() {
        return CollectionUtils.safeSize(banners);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
