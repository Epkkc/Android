package com.elegion.myfirstapplication.comments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elegion.myfirstapplication.APIUtils;
import com.elegion.myfirstapplication.Model.Album;
import com.elegion.myfirstapplication.Model.App;
import com.elegion.myfirstapplication.Model.Comment;
import com.elegion.myfirstapplication.Model.CommentLink;
import com.elegion.myfirstapplication.Model.PostComm;
import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.album.DetailAlbumFragment;
import com.elegion.myfirstapplication.db.MusicDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Album album;
    private Button btn;
    private EditText et;
    private CommAdapter adapter = new CommAdapter();
    ;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private Disposable subscribe;

    private boolean firstLoad = true;


    public static CommFragment newInstance(Album album) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailAlbumFragment.ALBUM_KEY, album);
        CommFragment fragment = new CommFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        album = (Album) getArguments().getSerializable(DetailAlbumFragment.ALBUM_KEY);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        onRefresh();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comm_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.comm_recycler);
        mRefresher = view.findViewById(R.id.comm_refresher);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.errorView);
        btn = view.findViewById(R.id.comment_push);
        et = view.findViewById(R.id.comment_input);

        btn.setOnClickListener(v -> OnClick());


    }

    private void handleComment(Comment comment) {
        getMusicDao().insertComment(comment);
        CommentLink link = new CommentLink(comment.getId(), album.getId());
        getMusicDao().insertCommLink(link);
        adapter.addComment(comment);
        mRefresher.setRefreshing(false);
        Toast.makeText(getContext(), "Комментарий успешно добавлен", Toast.LENGTH_SHORT).show();
    }

    private void handleCommentThrowable(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        throwable.printStackTrace();
        mRefresher.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            mRefresher.setRefreshing(true);
            getComments();
        });
    }


    private MusicDao getMusicDao() {
        return ((App) getActivity().getApplicationContext()).getMusicDatabase().getMusicDao();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_comms, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Переключение на песни

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, DetailAlbumFragment.newInstance(album))
                .addToBackStack(DetailAlbumFragment.class.getSimpleName())
                .commit();
        return true;
    }

    private void getComments() {
        AtomicBoolean noInt = new AtomicBoolean(false);
        Disposable subscribe = APIUtils.getAPI().getComments(album.getId())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(comments -> {
                    getMusicDao().insertComments(comments);
                    List<CommentLink> links = new ArrayList<>();
                    for (Comment com : comments) {
                        links.add(new CommentLink(com.getId(), album.getId()));
                    }
                    getMusicDao().insertCommLinks(links);
                })
                .onErrorReturn(throwable -> {
                    if (APIUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                        noInt.set(true);
                        return getMusicDao().getAlbumComments(album.getId());
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comments -> {
                            if (comments.size() == 0) {
                                mErrorView.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                            } else {
                                mErrorView.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            }
                            boolean refreshed = adapter.addData(comments, true);
                            mRefresher.setRefreshing(false);

                            if (firstLoad) {
                                firstLoad = false;
                            } else {
                                if (noInt.get()) {
                                    Toast.makeText(getContext(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                                    noInt.set(false);
                                } else {

                                    if (refreshed) {
                                        Toast.makeText(getContext(), "Комментарии обновлены", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Новых комментариев нет", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            mRefresher.setRefreshing(false);
                        });


    }

    @Override
    public void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }

        subscribe = null;

        super.onDestroy();
    }

    private void OnClick() {
        if (et.getText().toString() != null) {


            mRefresher.setRefreshing(true);
            PostComm comment = new PostComm();
            comment.setText(et.getText().toString());
            comment.setId(album.getId());

            if (subscribe != null && !subscribe.isDisposed()) {
                subscribe.dispose();
            }

            subscribe = APIUtils.getAPIWithOutConverter().postComment(comment)
                    .flatMap(comment1 -> APIUtils.getAPI().getCommentWithID(comment1.getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleComment, this::handleCommentThrowable);

        } else {
            Toast.makeText(getContext(), "Нет текста", Toast.LENGTH_SHORT).show();
        }
    }

}
