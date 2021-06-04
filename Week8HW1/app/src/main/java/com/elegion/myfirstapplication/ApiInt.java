package com.elegion.myfirstapplication;

import com.elegion.myfirstapplication.Model.Album;
import com.elegion.myfirstapplication.Model.Comment;
import com.elegion.myfirstapplication.Model.NewUser;
import com.elegion.myfirstapplication.Model.PostComm;
import com.elegion.myfirstapplication.Model.PostcomAns;
import com.elegion.myfirstapplication.Model.Song;
import com.elegion.myfirstapplication.Model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInt {

    @POST("registration")
    Completable registration(@Body User user);

    @GET("albums")
    Single<List<Album>> getAlbums();

    @GET("albums/{id}")
    Single<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<List<Song>> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);

    @GET("user")
    Observable<NewUser> getUser();

    @GET("albums/{id}/comments")
    Single<List<Comment>> getComments(@Path("id") int id);

    @POST("/api/comments")
    Single<PostcomAns> postComment(@Body PostComm comm);

    @GET("/api/comments/{id}")
    Single<Comment> getCommentWithID(@Path("id") int id);


}
