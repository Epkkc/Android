package com.example.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit retrofit;

    private static OkHttpClient client;

    public static OkHttpClient getClient(){
        if (client != null) return client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    public static Retrofit getRetrofit(){
        if (retrofit != null) return retrofit;
        retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .client(getClient())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
        return retrofit;
    }

    public static API getAPIService(){
        return getRetrofit().create(API.class);
    }
}
