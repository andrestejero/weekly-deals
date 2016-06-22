package com.andrestejero.weeklydeals.repositories;

import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.network.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AppRepository {

    private ServiceGenerator.GetGameListServiceApi mService;
    private Call<List<Game>> mCallGetGames;

    public AppRepository() {
        mService = ServiceGenerator.createService(ServiceGenerator.GetGameListServiceApi.class);
    }

    public void getGames(Callback<List<Game>> callback) {
        mCallGetGames = mService.getGames();
        mCallGetGames.enqueue(callback);
    }

    public void stopGetGames() {
        if (mCallGetGames != null) {
            mCallGetGames.cancel();
        }
    }

}
