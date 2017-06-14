package com.andrestejero.weeklydeals.network;

import com.andrestejero.weeklydeals.models.HomeContainer;
import com.andrestejero.weeklydeals.models.ProductDetail;
import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.models.SearchContainer;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class ServiceGenerator {

    public interface WeeklyDealsServiceApi {
        @GET("/lists/{id}")
        Call<PsnContainer> getPsnContainer(@Path("id") String id, @Query("offset") Integer offset, @Query("sorting") String sortApplied,@QueryMap(encoded = false) Map<String, String> filters);

        @GET("/products/{id}")
        Call<ProductDetail> getProductDetail(@Path("id") String id);

        @GET("/home")
        Call<HomeContainer> getHomeContainer();

        @GET("/games")
        Call<SearchContainer> getAutocomplete(@Query("name") String searchString, @Query("suggested") boolean suggested);
    }

    public static final String API_BASE_URL = "http://ps-mapi.herokuapp.com";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        // Agrego logs de Retrofit
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
