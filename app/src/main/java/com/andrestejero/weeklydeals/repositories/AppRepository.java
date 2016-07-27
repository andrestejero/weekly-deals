package com.andrestejero.weeklydeals.repositories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.helpers.FilterHelper;
import com.andrestejero.weeklydeals.models.Filter;
import com.andrestejero.weeklydeals.models.ProductDetail;
import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.network.ServiceGenerator;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class AppRepository {

    private ServiceGenerator.WeeklyDealsServiceApi mService;
    private Call<PsnContainer> mCallGetPsnContainer;
    private Call<ProductDetail> mCallGetProductDetail;

    public AppRepository() {
        mService = ServiceGenerator.createService(ServiceGenerator.WeeklyDealsServiceApi.class);
    }

    public void getPsnContainer(@NonNull String id, @Nullable Integer offset, @Nullable List<Filter> filtersApplied, @NonNull Callback<PsnContainer> callback) {
        Map<String, String> filters = FilterHelper.getSelectedFilters(filtersApplied);
        mCallGetPsnContainer = mService.getPsnContainer(id, offset, filters);
        mCallGetPsnContainer.enqueue(callback);
    }

    public void stopGetPsnContainer() {
        if (mCallGetPsnContainer != null) {
            mCallGetPsnContainer.cancel();
        }
    }

    public void getProductDetail(@NonNull String id, @NonNull Callback<ProductDetail> callback) {
        mCallGetProductDetail = mService.getProductDetail(id);
        mCallGetProductDetail.enqueue(callback);
    }

    public void stopGetProductDetail() {
        if (mCallGetProductDetail != null) {
            mCallGetProductDetail.cancel();
        }
    }
}
