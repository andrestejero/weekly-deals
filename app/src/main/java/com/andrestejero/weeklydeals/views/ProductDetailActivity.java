package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Price;
import com.andrestejero.weeklydeals.models.ProductDetail;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.adapters.PsnListAdapterHelper;
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
                new ImageRequest(this, productDetail.getImage(), mViewHolder.productImage).widthInPixels(200, 1000).execute();
            } else {
                mViewHolder.productImage.setImageResource(R.drawable.bg_image_placeholder_200dp);
            }
            mViewHolder.productName.setText(productDetail.getName());
            mViewHolder.provider.setText(productDetail.getProvider());
            mViewHolder.platform.setText("PS4");
            mViewHolder.gameType.setText("GameType");
            mViewHolder.releaseDate.setText(productDetail.getReleaseDate());
            mViewHolder.rating.setText(productDetail.getRating());
            mViewHolder.description.setText(Html.fromHtml(productDetail.getDescription()));

            Price price = productDetail.getPrice();
            if (price != null) {
                PsnListAdapterHelper.showNormalPrice(price, mViewHolder.normalPrice, mViewHolder.discountPrice);
                PsnListAdapterHelper.showDiscountPrice(price, mViewHolder.discountPrice);
                PsnListAdapterHelper.showPlusPrice(price, mViewHolder.plusPrice);
                PsnListAdapterHelper.showPlusBadge(price, mViewHolder.productBadge);
                PsnListAdapterHelper.updateDiscountContainer(price, mViewHolder.discount, mViewHolder.discountContainer);
                PsnListAdapterHelper.updateDiscountPlusContainer(price, mViewHolder.plusDiscount, mViewHolder.discountPlusContainer);
            }
        }
    }

    @Override
    public void showErrorProductDetail() {

    }

    private class ViewHolder {
        private View loadingView;
        private ImageView productImage;
        private TextView productName;
        private TextView provider;
        private TextView normalPrice;
        private TextView discountPrice;
        private View discountContainer;
        private TextView discount;
        private TextView plusPrice;
        private View discountPlusContainer;
        private TextView plusDiscount;
        private ImageView productBadge;
        private TextView platform;
        private TextView gameType;
        private TextView releaseDate;
        private TextView rating;
        private TextView description;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            productImage = (ImageView) findViewById(R.id.ivProductImage);
            productName = (TextView) findViewById(R.id.tvProductName);
            provider = (TextView) findViewById(R.id.tvProvider);
            normalPrice = (TextView) findViewById(R.id.tvNormalPrice);
            discountPrice = (TextView) findViewById(R.id.tvDiscountPrice);
            discountContainer = findViewById(R.id.llDiscountContainer);
            discount = (TextView) findViewById(R.id.tvDiscount);
            plusPrice = (TextView) findViewById(R.id.tvPlusPrice);
            discountPlusContainer = findViewById(R.id.llPlusDiscountContainer);
            plusDiscount = (TextView) findViewById(R.id.tvPlusDiscount);
            productBadge = (ImageView) findViewById(R.id.ivBadge);
            platform = (TextView) findViewById(R.id.tvPlatform);
            gameType = (TextView) findViewById(R.id.tvGameType);
            releaseDate = (TextView) findViewById(R.id.tvReleaseDate);
            rating = (TextView) findViewById(R.id.tvRating);
            description = (TextView) findViewById(R.id.tvDescription);
        }
    }
}
