package com.example.week6practice;

import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.week6practice.mock.MockAdapter;
import com.example.week6practice.mock.MockGenerator;

import java.net.URI;
import java.util.Random;

public class Recyclefragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycler;
    private ContactsAdapter adapter = new ContactsAdapter();
    private View errorView;
    private Random random = new Random();
    private ContactsAdapter.onItemClickListener listener;

    public static Recyclefragment newInstance() {
        return new Recyclefragment();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactsAdapter.onItemClickListener) {
            listener = (ContactsAdapter.onItemClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recycler = view.findViewById(R.id.recycler);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        errorView = view.findViewById(R.id.error_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
        adapter.setListener(listener);


    }

    @Override
    public void onRefresh() {


        getLoaderManager().restartLoader(0, null, this);


    }

    private void showdata(int count) {
//        adapter.addData(MockGenerator.generate(count), true);
//        errorView.setVisibility(View.GONE);
//        recycler.setVisibility(View.VISIBLE);
    }

    private void showError() {
        errorView.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(), ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, ContactsContract.Contacts._ID);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
