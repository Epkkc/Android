package com.elegion.myfirstapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostComm implements Serializable {
    @SerializedName("text")
    private String text;

    @SerializedName("album_id")
    private int id;

}
