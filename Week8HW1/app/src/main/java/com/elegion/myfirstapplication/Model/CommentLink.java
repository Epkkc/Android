package com.elegion.myfirstapplication.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(foreignKeys = {
        @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "album_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Comment.class, parentColumns = "id", childColumns = "comm_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
public class CommentLink implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;

    @SerializedName("comm_id")
    private int comm_id;

    @SerializedName("album_id")
    private int album_id;

    @Ignore
    public CommentLink(int comm_id, int album_id) {
        this.comm_id = comm_id;
        this.album_id = album_id;
    }
}
