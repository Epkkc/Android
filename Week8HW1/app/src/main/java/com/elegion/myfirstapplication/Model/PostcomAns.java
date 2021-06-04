package com.elegion.myfirstapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostcomAns implements Serializable {

    @SerializedName("id")
    private int id;
}
