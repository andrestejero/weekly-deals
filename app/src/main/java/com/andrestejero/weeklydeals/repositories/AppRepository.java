package com.andrestejero.weeklydeals.repositories;

import com.andrestejero.weeklydeals.models.ProductList;
import com.andrestejero.weeklydeals.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;

public class AppRepository {

    private ServiceGenerator.WeeklyDealsServiceApi mService;
    private Call<ProductList> mCallGetGames;

    public AppRepository() {
        mService = ServiceGenerator.createService(ServiceGenerator.WeeklyDealsServiceApi.class);
    }

    public void getGames(Callback<ProductList> callback) {
        mCallGetGames = mService.getGames("STORE-MSF77008-ALLDEALS");
        mCallGetGames.enqueue(callback);
    }

    public void stopGetGames() {
        if (mCallGetGames != null) {
            mCallGetGames.cancel();
        }
    }

}
