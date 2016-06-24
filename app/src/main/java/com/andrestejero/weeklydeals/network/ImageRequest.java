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

    private final static String PROTOCOL = "https:";
    private final static int MULTIPLE_OF = 50;
    private static int WIDTH_DIVIDER = 1;

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

    public static void setDeviceIsLowEnd(boolean deviceIsLowEnd) {
        WIDTH_DIVIDER = deviceIsLowEnd ? 2 : 1;
    }

    public ImageRequest scaleType(@NonNull ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    @NonNull
    public ImageRequest placeHolderResourceId(@DrawableRes int placeholderResourceId) {
        this.placeholderResourceId = placeholderResourceId;
        return this;
    }

    @NonNull
    public ImageRequest widthInPixels(@NonNull Integer widthInPixels, @NonNull Integer maxWidthInPixels) {
        this.width = widthInPixels;
        this.maxWidth = maxWidthInPixels;
        return this;
    }

    @NonNull
    public ImageRequest widthInDps(@NonNull Integer widthInDps, @NonNull Integer maxWidthInPixels) {
        return widthInPixels(Math.round(context.getResources().getDisplayMetrics().density * widthInDps), maxWidthInPixels);
    }

    @NonNull
    public ImageRequest doNotShowBrokenImage() {
        shouldShowBrokenImageOnError = false;
        return this;
    }

    @NonNull
    public ImageRequest doNotShowPlaceholderWhileRequesting() {
        shouldShowPlaceHolder = false;
        return this;
    }

    @NonNull
    public ImageRequest showBrokenImageDarkBackgroundOnError() {
        shouldShowBrokenImageDarkBackgroundOnError = true;
        return this;
    }

    @NonNull
    public ImageRequest fitCenterInside() {
        shouldFitCenterInside = true;
        shouldFitCenterCrop = false;
        return this;
    }

    @NonNull
    public ImageRequest fitCenterCrop() {
        shouldFitCenterCrop = true;
        shouldFitCenterInside = false;
        return this;
    }

    public void execute(@Nullable final ImageRequestCallback listener) {
        String path = getPath();
        executeRequest(path, listener);
    }

    public void execute() {
        execute(null);
    }

    private void executeRequest(@NonNull String path, @Nullable final ImageRequestCallback listener) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //Picasso.with(context).setLoggingEnabled(BuildTypeUtils.isDebug());
        //Picasso.with(context).setIndicatorsEnabled(AppPreferences.isImageIndicatorsEnabled(context));
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

    @NonNull
    private String getPath() {
        if (url.contains("http")) {
            return url + getWidthSuffix();
        }
        return PROTOCOL + url + getWidthSuffix();
    }

    @NonNull
    private String getWidthSuffix() {
        if (width == null || maxWidth == null) {
            return "";
        }
        return doGetWidthSuffix(width, maxWidth);
    }

    @NonNull
    private String doGetWidthSuffix(int width, int maxWidth) {
        int imageSize = roundUp(Math.round(width) / WIDTH_DIVIDER, MULTIPLE_OF);
        maxWidth = roundDown(maxWidth / WIDTH_DIVIDER, MULTIPLE_OF);
        if (imageSize >= maxWidth) {
            return "_" + maxWidth;
        } else {
            return "_" + imageSize;
        }
    }

    private int roundUp(float number, int multipleOf) {
        return ((int) (number / multipleOf) + (number % multipleOf > 0 ? 1 : 0)) * multipleOf;
    }

    private int roundDown(float number, int multipleOf) {
        return (int) (number - (number % multipleOf));
    }

    public interface ImageRequestCallback {
        void onError();

        void onSuccess();
    }

}
