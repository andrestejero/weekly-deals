package com.andrestejero.weeklydeals.repositories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;

public class AppRepository {

    private ServiceGenerator.WeeklyDealsServiceApi mService;
    private Call<PsnContainer> mCallGetPsnContainer;

    public AppRepository() {
        mService = ServiceGenerator.createService(ServiceGenerator.WeeklyDealsServiceApi.class);
    }

    public void getPsnContainer(@NonNull String id, @NonNull Callback<PsnContainer> callback) {
        mCallGetPsnContainer = mService.getPsnContainer(id);
        mCallGetPsnContainer.enqueue(callback);
    }

    public void stopGetPsnContainer() {
        if (mCallGetPsnContainer != null) {
            mCallGetPsnContainer.cancel();
        }
    }

}
