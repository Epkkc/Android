package com.elegion.myfirstapplication.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.elegion.myfirstapplication.Model.Album;
import com.elegion.myfirstapplication.Model.Comment;
import com.elegion.myfirstapplication.Model.CommentLink;
import com.elegion.myfirstapplication.Model.Link;
import com.elegion.myfirstapplication.Model.Song;

import java.util.List;

@Dao
public interface MusicDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Query("SELECT * from album")
    List<Album> getAlbums();

    @Query("SELECT * from album where id=:id")
    Album getAlbum(int id);

    @Delete
    void deleteAlbum(Album albumDb);

    @Query("DELETE FROM album where id = :album")
    void deleteAlbumById(int album);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Query("SELECT * from song")
    List<Song> getSongs();

    @Query("select song.id, name, duration from song inner join link on song.id = link.song_id where album_id = :albumId")
    List<Song> getAlbumSongs(int albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLinks(List<Link> links);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Comment> comments);

    @Query("select comment.id, comment.text, comment.timestamp, comment.author, comment.album_id from comment inner join commentlink on comment.id = commentlink.comm_id where commentlink.album_id = :albumId")
    List<Comment> getAlbumComments(int albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCommLinks(List<CommentLink> comments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComment(Comment comments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCommLink(CommentLink commentLink);
}
