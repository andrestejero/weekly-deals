package com.andrestejero.weeklydeals.repositories;

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

    public void getPsnContainer(Callback<PsnContainer> callback) {
        mCallGetPsnContainer = mService.getPsnContainer("STORE-MSF77008-SAVE");
        mCallGetPsnContainer.enqueue(callback);
    }

    public void stopGetPsnContainer() {
        if (mCallGetPsnContainer != null) {
            mCallGetPsnContainer.cancel();
        }
    }

}
