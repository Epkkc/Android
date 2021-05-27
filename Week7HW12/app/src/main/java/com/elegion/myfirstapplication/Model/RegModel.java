package com.elegion.myfirstapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegModel implements Serializable {

    @SerializedName("errors")
    private ErrorsDTO errors;

    @NoArgsConstructor
    @Data
    public static class ErrorsDTO {
        @SerializedName("email")
        private List<String> email;
        @SerializedName("name")
        private List<String> name;
        @SerializedName("password")
        private List<String> password;
    }
}
