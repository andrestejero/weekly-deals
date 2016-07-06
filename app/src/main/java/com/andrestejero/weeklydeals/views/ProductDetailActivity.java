package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.ProductDetail;
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
        updateLoadingView(View.VISIBLE);
    }

    @Override
    public void showProductDetail(@NonNull ProductDetail productDetail) {
        updateLoadingView(View.GONE);
        if (mViewHolder != null) {
            mViewHolder.title.setText(productDetail.getDescription());
        }
    }

    @Override
    public void showErrorProductDetail() {

    }

    // TODO Pasar a AppBase
    private void updateLoadingView(int loadingVisibility) {
        if (mViewHolder != null) {
            mViewHolder.loadingView.setVisibility(loadingVisibility);
        }
    }

    private class ViewHolder {
        private View loadingView;
        private final TextView title;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            title = (TextView) findViewById(R.id.tvGameDetail);
        }
    }
}
