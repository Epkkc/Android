package com.elegion.myfirstapplication.album;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elegion.myfirstapplication.APIUtils;
import com.elegion.myfirstapplication.Model.App;
import com.elegion.myfirstapplication.Model.Comment;
import com.elegion.myfirstapplication.Model.Link;
import com.elegion.myfirstapplication.Model.Song;
import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.Model.Album;
import com.elegion.myfirstapplication.comments.CommFragment;
import com.elegion.myfirstapplication.db.MusicDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DetailAlbumFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String ALBUM_KEY = "ALBUM_KEY";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private Album mAlbum;

    @NonNull
    private final SongsAdapter mSongsAdapter = new SongsAdapter();

    public static DetailAlbumFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putSerializable(ALBUM_KEY, album);

        DetailAlbumFragment fragment = new DetailAlbumFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mRefresher = view.findViewById(R.id.refresher);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        mAlbum = (Album) getArguments().getSerializable(ALBUM_KEY);
        getActivity().setTitle(mAlbum.getName());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSongsAdapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            mRefresher.setRefreshing(true);
            getAlbum();
        });
    }

    private void getAlbum() {


        Disposable subscribe = APIUtils.getAPI().getAlbum(mAlbum.getId())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(album -> {
                    getMusicDao().insertSongs(album.getSongs());
                    List<Link> links = new ArrayList<>();
                    for (Song song : album.getSongs()) {
                        links.add(new Link(album.getId(), song.getId()));
                    }
                    getMusicDao().insertLinks(links);
                })
                .onErrorReturn(new Function<Throwable, Album>() {
                    @Override
                    public Album apply(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        if (APIUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                            return getMusicDao().getAlbum(mAlbum.getId());
                        }
                        return null;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(album -> {
                    mErrorView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    List<Song> songs = getMusicDao().getAlbumSongs(album.getId());
                    mSongsAdapter.addData(songs, true);
                    mRefresher.setRefreshing(false);
                }, throwable -> {
                    mErrorView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    mRefresher.setRefreshing(false);
                });
        //subscribe.dispose();
    }

    private MusicDao getMusicDao() {
        return ((App) getActivity().getApplicationContext()).getMusicDatabase().getMusicDao();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_songs, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, CommFragment.newInstance(mAlbum))
                .addToBackStack(CommFragment.class.getSimpleName())
                .commit();
        //TODO Переключение на комментарии


        return true;
    }
}

