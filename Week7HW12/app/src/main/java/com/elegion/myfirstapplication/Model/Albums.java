package com.elegion.myfirstapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Albums {

    @SerializedName("data")
    private List<DataDTO> data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("songs_count")
        private Integer songsCount;
        @SerializedName("release_date")
        private String releaseDate;
    }
}
