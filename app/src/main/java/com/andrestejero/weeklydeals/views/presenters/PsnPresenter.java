package com.andrestejero.weeklydeals.views.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.FilterApplied;
import com.andrestejero.weeklydeals.models.Product;
import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.repositories.AppRepository;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsnPresenter {

    private static final String LOG_TAG = PsnPresenter.class.getSimpleName();

    @NonNull
    private final WeakReference<PsnListView> weakView;
    @NonNull
    private final AppRepository mAppRepository;

    private Integer offset;

    @Nullable
    private List<Product> products;

    @Nullable
    private List<Category> categories;

    @Nullable
    private List<FilterApplied> filtersApplied;

    public PsnPresenter(@NonNull PsnListView view, @NonNull AppRepository appRepository) {
        this.weakView = new WeakReference<>(view);
        this.mAppRepository = appRepository;
        this.filtersApplied = new ArrayList<>();
    }

    public void getPsnContainer(@NonNull String id, final boolean nextPage, @Nullable List<FilterApplied> filters) {
        if (!nextPage) {
            offset = null;
        }
        PsnListView view = weakView.get();
        if (view != null) {
            if (CollectionUtils.isNullOrEmpty(products) && CollectionUtils.isNullOrEmpty(categories)) {
                view.showLoading();
            }
            mAppRepository.getPsnContainer(id, offset, filters, new Callback<PsnContainer>() {
                @Override
                public void onResponse(Call<PsnContainer> call, Response<PsnContainer> response) {
                    PsnListView view = weakView.get();
                    if (view != null) {
                        if (response.isSuccessful()) {
                            PsnContainer psnContainer = response.body();
                            if (!nextPage) {
                                offset = 0;
                            }
                            offset += psnContainer.getItemsCount();
                            showPsnList(psnContainer, view, nextPage);
                        } else {
                            view.showErrorGameList();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PsnContainer> call, Throwable t) {
                    PsnListView view = weakView.get();
                    if (view != null) {
                        view.showErrorGameList();
                    }
                }
            });
        }
    }

    private void showPsnList(@NonNull PsnContainer psnContainer, @NonNull PsnListView view, boolean nextPage) {
        if (CollectionUtils.isNotEmpty(psnContainer.getProducts()) && CollectionUtils.isNotEmpty(products) && nextPage) {
            int positionStart = products.size();
            int itemCount = psnContainer.getItemsCount();
            products.addAll(psnContainer.getProducts());
            psnContainer.setProducts(products);
            view.refreshPsnContainer(psnContainer, positionStart, itemCount);
        } else if (CollectionUtils.isNotEmpty(psnContainer.getCategories()) && CollectionUtils.isNotEmpty(categories) && nextPage) {
            int positionStart = categories.size();
            int itemCount = psnContainer.getItemsCount();
            categories.addAll(psnContainer.getCategories());
            psnContainer.setCategories(categories);
            view.refreshPsnContainer(psnContainer, positionStart, itemCount);
        } else if (CollectionUtils.isNotEmpty(psnContainer.getProducts())) {
            products = psnContainer.getProducts();
            psnContainer.setProducts(products);
            view.showPsnContainer(psnContainer);
        } else if (CollectionUtils.isNotEmpty(psnContainer.getCategories())) {
            categories = psnContainer.getCategories();
            psnContainer.setCategories(categories);
            view.showPsnContainer(psnContainer);
        } else {
            view.showEmptyList();
        }
    }

    public void updateFiltersApplied(@Nullable String filterId, @Nullable String valueId) {
        if (filtersApplied != null) {
            if (StringUtils.isNotEmpty(filterId) && StringUtils.isNotEmpty(valueId)) {
                boolean addFilter = true;
                for (Iterator<FilterApplied> iterator = filtersApplied.iterator(); iterator.hasNext();) {
                    FilterApplied filter = iterator.next();
                    if (StringUtils.isNotEmpty(filter.getId()) && StringUtils.isNotEmpty(filter.getValue())) {
                        if (filter.getId().equalsIgnoreCase(filterId) && filter.getValue().equalsIgnoreCase(valueId)) {
                            addFilter = false;
                            iterator.remove();
                        }
                    }
                }
                if (addFilter) {
                    filtersApplied.add(new FilterApplied(filterId, valueId));
                }
            }
            PsnListView view = weakView.get();
            if (view != null) {
                view.updateFiltersApplied(filtersApplied);
            }
        }
    }

    public interface PsnListView {
        void showLoading();

        void showPsnContainer(@NonNull PsnContainer psnContainer);

        void showEmptyList();

        void showErrorGameList();

        void refreshPsnContainer(@NonNull PsnContainer psnContainer, int positionStart, int itemCount);

        void updateFiltersApplied(@NonNull List<FilterApplied> filtersApplied);
    }
}
