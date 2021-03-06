package com.elegion.myfirstapplication.album;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.Model.Song;

public class SongsHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mDuration;

    public SongsHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mDuration = itemView.findViewById(R.id.tv_duration);
    }

    public void bind(Song item) {
        mTitle.setText(item.getName());
        mDuration.setText(item.getDuration());
    }
}
