package com.example.gitapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.data.ListActivityInterface;
import com.example.data.ListActivityPresenter;
import com.example.data.RefreshInterface;
import com.example.domain.model.User;

import java.util.List;

public class ListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ListActivityInterface, RefreshInterface {
    public static final String USERNAME = "USERNAME";
    public static final String USERNAME_KEY = "USERNAME_KEY";

    private ListActivityPresenter presenter;

    private RecyclerView recycler;
    private View errorView;
    private ListAdapter adapter;
    private SwipeRefreshLayout refresh;

    private OnRecyclerItemClick listener = username -> {
        Intent intent = new Intent(ListActivity.this, ProfileActivity.class);
        intent.putExtra(USERNAME_KEY, username);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_layout);

        recycler = findViewById(R.id.recycler_view);
        errorView = findViewById(R.id.error_layout);
        refresh = findViewById(R.id.swipe_refresh);
        refresh.setOnRefreshListener(this);

        presenter = new ListActivityPresenter();
        presenter.setView(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListAdapter();
        adapter.setListener(listener);
        recycler.setAdapter(adapter);


        presenter.loadUsers();
    }

    @Override
    public void onRefresh() {
        presenter.loadUsers();
    }

    @Override
    public void presentData(List<User> users) {
        recycler.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        adapter.setData(users);
    }

    @Override
    public void failure() {
        // При неудачном запросе
        recycler.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLoading() {
        refresh.setRefreshing(true);
    }

    @Override
    public void resetLoading() {
        refresh.setRefreshing(false);
    }
}