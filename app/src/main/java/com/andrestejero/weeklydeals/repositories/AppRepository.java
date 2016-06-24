package com.andrestejero.weeklydeals.repositories;

import com.andrestejero.weeklydeals.models.GameList;
import com.andrestejero.weeklydeals.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;

public class AppRepository {

    private ServiceGenerator.GetGameListServiceApi mService;
    private Call<GameList> mCallGetGames;

    public AppRepository() {
        mService = ServiceGenerator.createService(ServiceGenerator.GetGameListServiceApi.class);
    }

    public void getGames(Callback<GameList> callback) {
        mCallGetGames = mService.getGames("1");
        mCallGetGames.enqueue(callback);
    }

    public void stopGetGames() {
        if (mCallGetGames != null) {
            mCallGetGames.cancel();
        }
    }

}
