package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.util.Log;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.network.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppBaseActivity {

    @Override
    protected void onStart() {
        super.onStart();

        ServiceGenerator.GetPostByIdServiceApi service = ServiceGenerator.createService(ServiceGenerator.GetPostByIdServiceApi.class);

        Call<List<Game>> call = service.getGames();
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                List<Game> games = response.body();
                for (Game game : games) {
                    Log.d("onResponse", game.getDescription());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        //call.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
