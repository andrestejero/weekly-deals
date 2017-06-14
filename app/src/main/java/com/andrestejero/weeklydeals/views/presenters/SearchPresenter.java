package com.andrestejero.weeklydeals.views.presenters;

import android.support.annotation.NonNull;

import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.SearchContainer;
import com.andrestejero.weeklydeals.repositories.AppRepository;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {

    private static final String LOG_TAG = SearchPresenter.class.getSimpleName();

    @NonNull
    private final WeakReference<View> weakView;
    @NonNull
    private final AppRepository mAppRepository;

    public SearchPresenter(@NonNull View view, @NonNull AppRepository mAppRepository) {
        this.weakView = new WeakReference<>(view);
        this.mAppRepository = mAppRepository;
    }

    public void getProductSearch(@NonNull String searchString) {
        mAppRepository.getProductSearch(searchString, new Callback<SearchContainer>() {
            @Override
            public void onResponse(Call<SearchContainer> call, Response<SearchContainer> response) {
                View view = weakView.get();
                if (view != null) {
                    if (response.body() != null) {
                        if (CollectionUtils.isNotEmpty(response.body().getProducts())) {
                            view.updateList(response.body().getProducts());
                        } else {
                            view.showEmptyList();
                        }
                    } else {
                        view.showError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchContainer> call, Throwable t) {
                View view = weakView.get();
                if (view != null) {
                    view.showError();
                }
            }
        });
    }

    public interface View {
        void updateList(@NonNull List<Category> products);

        void showEmptyList();

        void showError();
    }
}
