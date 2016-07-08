package com.andrestejero.weeklydeals.views.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andrestejero.weeklydeals.models.ProductDetail;
import com.andrestejero.weeklydeals.repositories.AppRepository;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailPresenter {

    private static final String LOG_TAG = ProductDetailPresenter.class.getSimpleName();

    @NonNull
    private final WeakReference<DetailView> weakView;
    @NonNull
    private final AppRepository mAppRepository;

    public ProductDetailPresenter(@NonNull DetailView view, @NonNull AppRepository appRepository) {
        this.weakView = new WeakReference<>(view);
        this.mAppRepository = appRepository;
    }

    public void getProductDetailById(@NonNull String id) {
        DetailView view = weakView.get();
        if (view != null) {
            view.showLoading();
            mAppRepository.getProductDetail(id, new Callback<ProductDetail>() {
                @Override
                public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                    if (response.isSuccessful()) {
                        DetailView view = weakView.get();
                        if (view != null) {
                            ProductDetail productDetail = response.body();
                            if (productDetail != null) {
                                view.showProductDetail(productDetail);
                            } else {
                                view.showEmptyProductDetail();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductDetail> call, Throwable t) {
                    DetailView view = weakView.get();
                    if (view != null) {
                        view.showErrorProductDetail();
                    }
                }
            });
        }
    }

    public interface DetailView {
        void showLoading();

        void showProductDetail(@NonNull ProductDetail productDetail);

        void showEmptyProductDetail();

        void showErrorProductDetail();
    }

}
