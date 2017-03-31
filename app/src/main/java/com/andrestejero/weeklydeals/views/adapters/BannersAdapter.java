package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Banner;
import com.andrestejero.weeklydeals.models.Target;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.List;

public class BannersAdapter extends PagerAdapter {

    @NonNull
    private final Context context;

    @NonNull
    private final List<Banner> banners;

    @Nullable
    private OnBannerClickListener mlistener;

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
        loadImage(banner, (ImageView) layout.findViewById(R.id.bannerImage));
        setListener(banner, layout.findViewById(R.id.actionable));
    }

    private void loadImage(@NonNull Banner banner, @NonNull ImageView image) {
        if (StringUtils.isNotEmpty(banner.getImage())) {
            new ImageRequest(context, banner.getImage(), image)
                    .placeHolderResourceId(R.drawable.bg_image_placeholder_horizontal_360)
                    .showBrokenImageDarkBackgroundOnError()
                    .execute();
        }
    }

    private void setListener(@NonNull final Banner banner, @NonNull View actionable) {
        if (banner.getTarget() != null) {
            actionable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mlistener != null) {
                        mlistener.onBannerClick(banner.getTarget());
                    }
                }
            });
        }
    }

    public interface OnBannerClickListener {
        void onBannerClick(@NonNull Target target);
    }

    public void setOnBannerClickListener(@Nullable OnBannerClickListener listener) {
        this.mlistener = listener;
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
