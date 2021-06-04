package com.elegion.myfirstapplication.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@Data
public class Album implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @SerializedName("songs")
    @Ignore
    private List<Song> songs;

    @ColumnInfo(name = "release")
    @SerializedName("release_date")
    private String releaseDate;


}
