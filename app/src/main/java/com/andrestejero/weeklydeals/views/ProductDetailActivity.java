package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.ProductDetail;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.presenters.ProductDetailPresenter;

public class ProductDetailActivity extends AppBaseActivity implements ProductDetailPresenter.DetailView {

    private static final String LOG_TAG = ProductDetailActivity.class.getSimpleName();

    public static final String EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID";

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private ProductDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString(EXTRA_PRODUCT_ID);

        mViewHolder = new ViewHolder();
        mPresenter = new ProductDetailPresenter(this, getAppRepository());
        if (StringUtils.isNotEmpty(id)) {
            loadProductDetail(id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetProductDetail();
    }

    private void loadProductDetail(@NonNull String id) {
        if (mPresenter != null) {
            mPresenter.getProductDetailById(id);
        }
    }

    @Override
    public void showLoading() {
        if (mViewHolder != null) {
            updateLoadingView(mViewHolder.loadingView, View.VISIBLE);
        }
    }

    @Override
    public void showProductDetail(@NonNull ProductDetail productDetail) {
        if (mViewHolder != null) {
            updateLoadingView(mViewHolder.loadingView, View.GONE);
            if (StringUtils.isNotEmpty(productDetail.getImage())) {
                new ImageRequest(this, productDetail.getImage(), mViewHolder.defaultImage).widthInPixels(200, 1000).execute();
            } else {
                mViewHolder.defaultImage.setImageResource(R.drawable.bg_image_placeholder_200dp);
            }
            mViewHolder.name.setText(productDetail.getName());
            mViewHolder.provider.setText(productDetail.getProvider());
        }
    }

    @Override
    public void showErrorProductDetail() {

    }

    private class ViewHolder {
        private View loadingView;
        private final ImageView defaultImage;
        private final TextView name;
        private final TextView provider;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            defaultImage = (ImageView) findViewById(R.id.ivDetailImage);
            name = (TextView) findViewById(R.id.tvDetailName);
            provider = (TextView) findViewById(R.id.tvDetailProvider);
        }
    }
}
