package com.elegion.myfirstapplication.albums;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.myfirstapplication.Model.Album;
import com.elegion.myfirstapplication.R;

/**
 * @author Azret Magometov
 */

public class AlbumsHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mReleaseDate;

    public AlbumsHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mReleaseDate = itemView.findViewById(R.id.tv_release_date);
    }

    public void bind(Album item, AlbumsAdapter.OnItemClickListener onItemClickListener) {
        mTitle.setText(item.getName());
        mReleaseDate.setText(item.getReleaseDate());
        if (onItemClickListener != null) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(item));
        }
    }
}
