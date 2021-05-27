package com.elegion.myfirstapplication.Model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Song {
    @SerializedName("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("duration")
        private String duration;
    }
}
