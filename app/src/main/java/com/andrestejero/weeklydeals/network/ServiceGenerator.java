package com.andrestejero.weeklydeals.network;

import com.andrestejero.weeklydeals.models.Game;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ServiceGenerator {

    public interface GetPostByIdServiceApi {
        //@GET("/weekly-deals/{id}")
        //Call<Game> getItem(@Path("id") String id);

        @GET("/games")
        Call<List<Game>> getGames();
    }

    public static final String API_BASE_URL = "http://quick-entities.herokuapp.com";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
