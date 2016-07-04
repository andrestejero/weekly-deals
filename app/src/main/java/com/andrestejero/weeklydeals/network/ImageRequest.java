package com.andrestejero.weeklydeals.network;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.andrestejero.weeklydeals.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImageRequest {

    @NonNull
    private final Context context;
    @NonNull
    private final String url;
    @NonNull
    private final ImageView imageView;
    @DrawableRes
    private int placeholderResourceId;
    @Nullable
    private Integer width;
    @Nullable
    private Integer maxWidth;
    @NonNull
    private ImageView.ScaleType scaleType;
    private boolean shouldShowBrokenImageOnError;
    private boolean shouldShowBrokenImageDarkBackgroundOnError;
    private boolean shouldShowPlaceHolder;
    private boolean shouldFitCenterInside;
    private boolean shouldFitCenterCrop;

    public ImageRequest(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView) {
        this.context = context;
        this.url = url;
        this.imageView = imageView;
        this.placeholderResourceId = R.drawable.bg_image_placeholder_100dp;
        this.width = null;
        this.maxWidth = null;
        this.scaleType = ImageView.ScaleType.CENTER_CROP;
        this.shouldShowBrokenImageOnError = true;
        this.shouldShowBrokenImageDarkBackgroundOnError = false;
        this.shouldShowPlaceHolder = true;
        this.shouldFitCenterInside = false;
        this.shouldFitCenterCrop = false;
    }

    @NonNull
    public ImageRequest widthInPixels(@NonNull Integer widthInPixels, @NonNull Integer maxWidthInPixels) {
        this.width = widthInPixels;
        this.maxWidth = maxWidthInPixels;
        return this;
    }

    public void execute(@Nullable final ImageRequestCallback listener) {
        executeRequest(url, listener);
    }

    public void execute() {
        execute(null);
    }

    private void executeRequest(@NonNull String path, @Nullable final ImageRequestCallback listener) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // TODO: Picasso DEBUG
        Picasso.with(context).setLoggingEnabled(false);
        Picasso.with(context).setIndicatorsEnabled(false);
        RequestCreator request = Picasso.with(context)
                .load(path);
        if (shouldShowPlaceHolder) {
            request.placeholder(placeholderResourceId);
        }
        if (shouldShowBrokenImageOnError) {
            if (shouldShowBrokenImageDarkBackgroundOnError) {
                request.error(R.drawable.ic_broken_image_white);
            } else {
                request.error(R.drawable.ic_broken_image);
            }
        }
        if (shouldFitCenterInside) {
            request.fit().centerInside();
        }
        if (shouldFitCenterCrop) {
            request.fit().centerCrop();
        }
        request.into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                imageView.setScaleType(scaleType);
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onError() {
                if (shouldShowBrokenImageOnError) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                }
                if (shouldShowBrokenImageDarkBackgroundOnError) {
                    imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.grey20));
                }
                if (listener != null) {
                    listener.onError();
                }
            }
        });
    }

    public interface ImageRequestCallback {
        void onError();

        void onSuccess();
    }
}
