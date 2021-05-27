package com.elegion.myfirstapplication;

import com.elegion.myfirstapplication.Model.Album;
import com.elegion.myfirstapplication.Model.Albums;
import com.elegion.myfirstapplication.Model.NewUser;
import com.elegion.myfirstapplication.Model.RegModel;
import com.elegion.myfirstapplication.Model.Song;
import com.elegion.myfirstapplication.Model.Songs;
import com.elegion.myfirstapplication.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInt {

    @POST("registration")
    Call<RegModel> registration(@Body User user);

    @GET("albums")
    Call<Albums> getAlbums();

    @GET("albums/{id}")
    Call<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<Songs> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);

    @GET("user/")
    Call<NewUser> getUser();
}
