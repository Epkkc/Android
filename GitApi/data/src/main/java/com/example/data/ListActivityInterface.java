package com.example.data;

import com.example.domain.model.User;

import java.util.List;

public interface ListActivityInterface extends RefreshInterface {

    void presentData(List<User> users);

    void failure();

}
