package com.andrestejero.weeklydeals.repositories;

import com.andrestejero.weeklydeals.models.GameList;
import com.andrestejero.weeklydeals.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;

public class AppRepository {

    private ServiceGenerator.WeeklyDealsServiceApi mService;
    private Call<GameList> mCallGetGames;

    public AppRepository() {
        mService = ServiceGenerator.createService(ServiceGenerator.WeeklyDealsServiceApi.class);
    }

    public void getGames(Callback<GameList> callback) {
        mCallGetGames = mService.getGames("STORE-MSF77008-ALLDEALS");
        mCallGetGames.enqueue(callback);
    }

    public void stopGetGames() {
        if (mCallGetGames != null) {
            mCallGetGames.cancel();
        }
    }

}
