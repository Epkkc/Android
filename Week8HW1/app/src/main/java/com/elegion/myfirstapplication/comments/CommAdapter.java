package com.elegion.myfirstapplication.comments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.myfirstapplication.Model.Comment;
import com.elegion.myfirstapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CommAdapter extends RecyclerView.Adapter<CommHolder> {


    private final List<Comment> comments = new ArrayList<>();


    @Override
    public CommHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comm_layout, parent, false);
        return new CommHolder(view);
    }

    @Override
    public void onBindViewHolder(CommHolder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public boolean addData(List<Comment> comments1, boolean isRefreshed){
        boolean refresh;
        if (comments1.size()==comments.size()){
            refresh = false;
        } else{
            refresh = true;
        }
        if (isRefreshed){
            comments.clear();
        }
        comments.addAll(comments1);
        notifyDataSetChanged();
        return refresh;
    }

    public void addComment(Comment comment){
        comments.add(comment);
        notifyDataSetChanged();
    }


}

