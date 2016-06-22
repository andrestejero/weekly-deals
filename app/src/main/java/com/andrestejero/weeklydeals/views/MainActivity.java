package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.util.Log;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAppRepository().getGames(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    List<Game> games = response.body();
                    for (Game game : CollectionUtils.safeIterable(games)) {
                        Log.d("onResponse --> ", game.getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetGames();
    }
}
