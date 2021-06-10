package com.example.data;

import com.example.domain.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivityPresenter {

    private ListActivityInterface view;


    public void loadUsers(){
        view.setLoading();
        Utils.getAPIService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                view.presentData(response.body());
                view.resetLoading();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                view.failure();
                view.resetLoading();
            }
        });
    }

    public void setView(ListActivityInterface view) {
        this.view = view;
    }
}
