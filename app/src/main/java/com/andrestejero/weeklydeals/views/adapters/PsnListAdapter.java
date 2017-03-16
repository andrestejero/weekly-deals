package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.Price;
import com.andrestejero.weeklydeals.models.Product;
import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.models.PsnViewType;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.Collections;
import java.util.List;

public class PsnListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = PsnListAdapter.class.getSimpleName();

    @NonNull
    private Context mContext;

    @Nullable
    private OnItemClickListener mItemClickListener;

    @Nullable
    private OnPageLoadingListener mPageLoadingListener;

    @NonNull
    private List<Category> mCategories;

    @NonNull
    private List<Product> mProducts;

    private int mPagingTotal;

    public PsnListAdapter(@NonNull Context context) {
        this.mContext = context;
        mCategories = Collections.emptyList();
        mProducts = Collections.emptyList();
    }

    public void updatePsnList(@NonNull PsnContainer psnContainer, int pagingTotal) {
        this.mCategories = CollectionUtils.safeList(psnContainer.getCategories());
        this.mProducts = CollectionUtils.safeList(psnContainer.getProducts());
        this.mPagingTotal = pagingTotal;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PsnViewType.CATEGORY.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.psn_category_item, parent, false);
            return new CategoryViewHolder(view);
        } else if (viewType == PsnViewType.LOADING.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.psn_loading_item, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.psn_game_item, parent, false);
            return new ProductViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isPositionPageLoading(position)) {
            if (mPageLoadingListener != null) {
                mPageLoadingListener.onPageLoading();
            }
        } else if (isPositionCategory(position)) {
            CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
            final Category category = mCategories.get(position);
            if (StringUtils.isNotEmpty(category.getName()) && category.getName().contains("----")) {
                viewHolder.categoryContent.setVisibility(View.GONE);
                viewHolder.separator.setVisibility(View.GONE);
            } else {
                viewHolder.separator.setVisibility(View.VISIBLE);
                viewHolder.categoryContent.setVisibility(View.VISIBLE);
                PsnListAdapterHelper.showCategoryName(category, viewHolder.categoryName);
                PsnListAdapterHelper.showCategoryImage(mContext, category, viewHolder.categoryImage);
            }
        } else {
            ProductViewHolder viewHolder = (ProductViewHolder) holder;
            position -= CollectionUtils.safeSize(mCategories);
            final Product product = mProducts.get(position);
            PsnListAdapterHelper.showProductName(product, viewHolder.productName);
            PsnListAdapterHelper.showProductImage(mContext, product, viewHolder.productImage);
            PsnListAdapterHelper.showPlatforms(product.getPlatforms(), viewHolder.platform);
            viewHolder.gameType.setText(product.getGameContentType());
            Price price = product.getPrice();
            if (price != null) {
                PsnListAdapterHelper.showNormalPrice(mContext, price, viewHolder.normalPrice, viewHolder.discountPrice);
                PsnListAdapterHelper.showDiscountPrice(mContext, price, viewHolder.discountPrice);
                PsnListAdapterHelper.showPlusPrice(mContext, price, viewHolder.plusPrice);
                PsnListAdapterHelper.showPlusBadge(price, viewHolder.productBadge);
                PsnListAdapterHelper.updateDiscountContainer(price, viewHolder.discount, viewHolder.discountContainer);
                PsnListAdapterHelper.updateDiscountPlusContainer(price, viewHolder.plusDiscount, viewHolder.discountPlusContainer);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionPageLoading(position)) {
            return PsnViewType.LOADING.ordinal();
        } else if (CollectionUtils.safeSize(mCategories) > position) {
            return PsnViewType.CATEGORY.ordinal();
        }
        return PsnViewType.PRODUCT.ordinal();
    }

    private boolean isPositionCategory(int position) {
        return getItemViewType(position) == PsnViewType.CATEGORY.ordinal();
    }

    private boolean isPositionPageLoading(int position) {
        return position == getTotalItems() && getTotalItems() < mPagingTotal;
    }

    private int getTotalItems() {
        return CollectionUtils.safeSize(mCategories) + CollectionUtils.safeSize(mProducts);
    }

    @Override
    public int getItemCount() {
        int count = getTotalItems();

        if (count < mPagingTotal) {
            count += 1;
        }

        return count;
    }

    public interface OnItemClickListener {
        void onCategoryClick(int position);
        void onItemClick(int position);
    }

    public interface OnPageLoadingListener {
        void onPageLoading();
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnPageLoadingListener(@Nullable OnPageLoadingListener listener) {
        this.mPageLoadingListener = listener;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View categoryContent;
        private View separator;
        private ImageView categoryImage;
        private TextView categoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryContent = itemView.findViewById(R.id.rlContent);
            separator = itemView.findViewById(R.id.separator);
            categoryImage = (ImageView) itemView.findViewById(R.id.ivCategoryImage);
            categoryName = (TextView) itemView.findViewById(R.id.tvCategoryName);
            View actionableCover = itemView.findViewById(R.id.actionableCover);
            actionableCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onCategoryClick(getLayoutPosition());
            }
        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView productImage;
        private TextView productName;
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

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.ivProductImage);
            productName = (TextView) itemView.findViewById(R.id.tvProductName);
            normalPrice = (TextView) itemView.findViewById(R.id.tvNormalPrice);
            discountPrice = (TextView) itemView.findViewById(R.id.tvDiscountPrice);
            discountContainer = itemView.findViewById(R.id.llDiscountContainer);
            discount = (TextView) itemView.findViewById(R.id.tvDiscount);
            plusPrice = (TextView) itemView.findViewById(R.id.tvPlusPrice);
            discountPlusContainer = itemView.findViewById(R.id.llPlusDiscountContainer);
            plusDiscount = (TextView) itemView.findViewById(R.id.tvPlusDiscount);
            productBadge = (ImageView) itemView.findViewById(R.id.ivBadge);
            platform = (TextView) itemView.findViewById(R.id.tvPlatform);
            gameType = (TextView) itemView.findViewById(R.id.tvGameType);
            View actionableCover = itemView.findViewById(R.id.actionableCover);
            actionableCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
