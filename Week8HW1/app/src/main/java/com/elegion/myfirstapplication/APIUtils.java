package com.elegion.myfirstapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.elegion.myfirstapplication.Model.DataConverterFactory;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtils {

    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static Retrofit retrofitWithOutConverter;
    private static Gson gson;
    private static ApiInt api;
    private static ApiInt apiWithOutConverter;
    private static String email = "";
    private static String password = "";



    public static OkHttpClient getBasicAuthClient(boolean newInstance) {
        if (newInstance || okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.authenticator(new Authenticator() {
                @Nullable
                @Override
                public Request authenticate(@NonNull Route route, @NonNull Response response) {
                    String credentials = Credentials.basic(email, password);
                    return response.request().newBuilder().header("Authorization", credentials).build();
                }
            });
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

            okHttpClient = builder.build();
        }

        return okHttpClient;
    }

    public static Retrofit getRetrofit() {
        if (gson == null) {
            gson = new Gson();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://android.academy.e-legion.com/api/")
                    .client(getBasicAuthClient(true))
                    .addConverterFactory(new DataConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitWithOutConverter() {
        if (gson == null) {
            gson = new Gson();
        }
        if (retrofitWithOutConverter == null) {
            retrofitWithOutConverter = new Retrofit.Builder()
                    .baseUrl("https://android.academy.e-legion.com/api/")
                    .client(getBasicAuthClient(true))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitWithOutConverter;
    }

    public static ApiInt getAPI() {
        if (api == null) {
            api = getRetrofit().create(ApiInt.class);
        }
        return api;
    }


    public static void setEmail(String email) {
        APIUtils.email = email;
    }

    public static void setPassword(String password) {
        APIUtils.password = password;
    }

    public static ApiInt getAPIWithOutConverter() {
        if (apiWithOutConverter == null) {
            apiWithOutConverter = getRetrofitWithOutConverter().create(ApiInt.class);
        }
        return apiWithOutConverter;
    }

}
