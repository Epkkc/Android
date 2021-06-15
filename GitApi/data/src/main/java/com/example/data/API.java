package com.example.data;

import com.example.domain.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface API {

    String token = "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";

    @Headers("Authorization:" + token)
    @GET("/users")
    Call<List<User>> getUsers();

    @Headers("Authorization:" + token)
    @GET("/users/{username}/followers")
    Call<List<User>> getFollowers(@Path("username") String username);

    @Headers("Authorization:" + token)
    @GET("/users/{username}")
    Call<User> getUserInfo(@Path("username") String username);
}
