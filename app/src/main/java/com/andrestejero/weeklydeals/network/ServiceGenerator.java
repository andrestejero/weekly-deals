package com.andrestejero.weeklydeals.network;

import com.andrestejero.weeklydeals.models.ProductList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ServiceGenerator {

    public interface WeeklyDealsServiceApi {
        @GET("/lists/{id}")
        Call<ProductList> getGames(@Path("id") String gameId);
    }

    public static final String API_BASE_URL = "http://ps-mapi.herokuapp.com";

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
