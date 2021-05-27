package com.elegion.myfirstapplication.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Album {

    @SerializedName("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("songs")
        private List<Song> songs;
        @SerializedName("release_date")
        private String releaseDate;


    }
}
