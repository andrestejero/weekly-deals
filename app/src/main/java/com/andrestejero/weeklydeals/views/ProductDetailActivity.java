package com.andrestejero.weeklydeals.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Price;
import com.andrestejero.weeklydeals.models.ProductDetail;
import com.andrestejero.weeklydeals.models.Rating;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.DateUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.adapters.PsnListAdapterHelper;
import com.andrestejero.weeklydeals.views.presenters.ProductDetailPresenter;

public class ProductDetailActivity extends AppBaseActivity implements ProductDetailPresenter.DetailView {

    private static final String LOG_TAG = ProductDetailActivity.class.getSimpleName();

    public static final String EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID";
    private final static int PRODUCT_DETAIL_IMAGE_WIDTH = 200;

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private ProductDetailPresenter mPresenter;

    @NonNull
    public static Intent newIntent(@NonNull Context context, @NonNull String productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail);
        setTitle(R.string.title_activity_game_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

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
        updateVisibilities(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }

    @Override
    public void showErrorProductDetail() {
        updateVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE);
    }

    @Override
    public void showEmptyProductDetail() {
        updateVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE);
    }

    @Override
    public void showProductDetail(@NonNull ProductDetail productDetail) {
        updateVisibilities(View.GONE, View.GONE, View.GONE, View.VISIBLE);
        setTitle(productDetail.getName());
        // FIXME Refactor
        if (mViewHolder != null) {
            if (StringUtils.isNotEmpty(productDetail.getImage())) {
                String url = productDetail.getImageFromWidth(PRODUCT_DETAIL_IMAGE_WIDTH);
                new ImageRequest(this, url, mViewHolder.productImage).widthInPixels(PRODUCT_DETAIL_IMAGE_WIDTH, 1000).execute();
            } else {
                mViewHolder.productImage.setImageResource(R.drawable.bg_image_placeholder_200dp);
            }
            mViewHolder.productName.setText(productDetail.getName());
            mViewHolder.provider.setText(productDetail.getProvider());

            PsnListAdapterHelper.showPlatforms(productDetail.getPlatforms(), mViewHolder.platform);

            mViewHolder.gameType.setText(productDetail.getGameContentType());

            if (productDetail.getReleaseDate() != null) {
                String releaseDate = DateUtils.serverDateFormat(productDetail.getReleaseDate(), getString(R.string.release_date_format));
                mViewHolder.releaseDate.setText(releaseDate);
            }

            Rating rating = productDetail.getRating();
            if (rating != null && rating.getAverage() != null) {
                mViewHolder.ratingBar.setRating(rating.getAverage());
                if (rating.getTotal() != null) {
                    String average = Float.toString(rating.getAverage());
                    String total = Integer.toString(rating.getTotal());
                    mViewHolder.ratingResult.setText(getString(R.string.rating_result, average, total));
                }
            }
            mViewHolder.description.setText(Html.fromHtml(productDetail.getDescription()));

            Price price = productDetail.getPrice();
            if (price != null) {
                PsnListAdapterHelper.showNormalPrice(this, price, mViewHolder.normalPrice, mViewHolder.discountPrice);
                PsnListAdapterHelper.showDiscountPrice(this, price, mViewHolder.discountPrice);
                PsnListAdapterHelper.showPlusPrice(this, price, mViewHolder.plusPrice);
                PsnListAdapterHelper.showPlusBadge(price, mViewHolder.productBadge);
                PsnListAdapterHelper.updateDiscountContainer(price, mViewHolder.discount, mViewHolder.discountContainer);
                PsnListAdapterHelper.updateDiscountPlusContainer(price, mViewHolder.plusDiscount, mViewHolder.discountPlusContainer);

                if (price.getStartDate() != null && price.getEndDate() != null) {
                    String startDate = DateUtils.dateFormat(price.getStartDate(), getString(R.string.format_date));
                    String endDate = DateUtils.dateFormat(price.getEndDate(), getString(R.string.format_date));
                    mViewHolder.priceAvailableDate.setText(getString(R.string.price_available_date, startDate, endDate));
                }
            }
        }
    }

    private void updateVisibilities(int loadingVisibility, int errorVisibility, int emptyVisibility, int contentVisibility) {
        if (mViewHolder != null) {
            mViewHolder.loadingView.setVisibility(loadingVisibility);
            mViewHolder.errorView.setVisibility(errorVisibility);
            mViewHolder.emptyView.setVisibility(emptyVisibility);
            mViewHolder.contentView.setVisibility(contentVisibility);
        }
    }

    private class ViewHolder {
        private View loadingView;
        private View errorView;
        private View emptyView;
        private View contentView;
        private ImageView productImage;
        private TextView productName;
        private RatingBar ratingBar;
        private TextView ratingResult;
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
        private TextView priceAvailableDate;
        private TextView description;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            errorView = findViewById(R.id.errorView);
            emptyView = findViewById(R.id.emptyView);
            contentView = findViewById(R.id.contentView);
            productImage = (ImageView) findViewById(R.id.ivProductImage);
            productName = (TextView) findViewById(R.id.tvProductName);
            ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            ratingResult = (TextView) findViewById(R.id.tvRatingResult);
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
            priceAvailableDate = (TextView) findViewById(R.id.tvPriceAvailable);
            description = (TextView) findViewById(R.id.tvDescription);
        }
    }
}
