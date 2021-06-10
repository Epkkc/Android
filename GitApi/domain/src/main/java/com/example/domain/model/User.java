package com.example.domain.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarURL;

//    @SerializedName("followers")
//    private int followersNumber;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

//    public int getFollowersNumber() {
//        return followersNumber;
//    }
//
//    public void setFollowersNumber(int followersNumber) {
//        this.followersNumber = followersNumber;
//    }

}
