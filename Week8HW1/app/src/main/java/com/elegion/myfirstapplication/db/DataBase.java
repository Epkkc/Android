package com.elegion.myfirstapplication.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.elegion.myfirstapplication.Model.Album;
import com.elegion.myfirstapplication.Model.Comment;
import com.elegion.myfirstapplication.Model.CommentLink;
import com.elegion.myfirstapplication.Model.Link;
import com.elegion.myfirstapplication.Model.Song;

@Database(entities = {Album.class, Song.class, Link.class, Comment.class, CommentLink.class}, version = 4)
public abstract class DataBase extends RoomDatabase {
    public abstract MusicDao getMusicDao();

}
