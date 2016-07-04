package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.models.Price;
import com.andrestejero.weeklydeals.network.ImageRequest;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.Collections;
import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = GameListAdapter.class.getSimpleName();

    private final static int PRODUCT_IMAGE_WIDTH = 100;

    @NonNull
    private Context mContext;

    @Nullable
    private OnItemClickListener mItemClickListener;

    @NonNull
    private List<Game> mGames;

    public GameListAdapter(@NonNull Context context) {
        this.mContext = context;
        mGames = Collections.emptyList();
    }

    public void updateGames(@Nullable List<Game> games) {
        this.mGames = CollectionUtils.safeList(games);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.game_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Game game = mGames.get(position);
        viewHolder.gameTitle.setText(game.getName());
        if (game.getPrice() != null) {
            Price price = game.getPrice();
            if (price.getNormal() != null) {
                viewHolder.normalPrice.setText(StringUtils.gamePrice(price.getNormal()));
            }
            if (price.getDiscountPrice() != null) {
                viewHolder.discountPrice.setText(StringUtils.gamePrice(price.getDiscountPrice()));
            }
            if (price.getDiscount() != null) {
                viewHolder.discount.setText(StringUtils.gamePercent(price.getDiscount()));
            }
            if (price.getBonusDiscountPrice() != null) {
                viewHolder.plusDiscountPrice.setVisibility(View.VISIBLE);
                viewHolder.plusDiscountPrice.setText(StringUtils.gamePrice(price.getBonusDiscountPrice()));
            } else {
                viewHolder.plusDiscountPrice.setVisibility(View.GONE);
            }
            if (price.getBonusDiscount() != null) {
                viewHolder.plusSavingContainer.setVisibility(View.VISIBLE);
                viewHolder.plusDiscount.setText(StringUtils.gamePercent(price.getBonusDiscount()));
            } else {
                viewHolder.plusSavingContainer.setVisibility(View.GONE);
            }
        }

        showGameImage(mContext, game, viewHolder.gameImage, PRODUCT_IMAGE_WIDTH);
    }

    private static void showGameImage(@NonNull Context context, @NonNull Game game, @NonNull ImageView imageView, @Nullable Integer imageWidth) {
        if (StringUtils.isNotEmpty(game.getImage()) && imageWidth != null) {
            new ImageRequest(context, game.getImage(), imageView).widthInPixels(imageWidth, 1000).execute();
        } else {
            imageView.setImageResource(R.drawable.bg_image_placeholder_100dp);
        }
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.safeSize(mGames);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView gameTitle;
        private ImageView gameImage;
        private TextView normalPrice;
        private TextView discountPrice;
        private TextView discount;
        private View plusSavingContainer;
        private TextView plusDiscountPrice;
        private TextView plusDiscount;

        public ViewHolder(View itemView) {
            super(itemView);
            View actionableCover = itemView.findViewById(R.id.actionableCover);
            gameTitle = (TextView) itemView.findViewById(R.id.tvGameTitle);
            gameImage = (ImageView) itemView.findViewById(R.id.ivGameImage);
            normalPrice = (TextView) itemView.findViewById(R.id.tvNormalPrice);
            discountPrice = (TextView) itemView.findViewById(R.id.tvDiscountPrice);
            discount = (TextView) itemView.findViewById(R.id.tvGameDiscount);
            plusSavingContainer = itemView.findViewById(R.id.llPlusSavingContainer);
            plusDiscountPrice = (TextView) itemView.findViewById(R.id.tvPlusDiscountPrice);
            plusDiscount = (TextView) itemView.findViewById(R.id.tvPlusGameDiscount);
            actionableCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }

}
