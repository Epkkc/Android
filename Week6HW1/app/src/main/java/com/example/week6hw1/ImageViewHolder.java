package com.example.week6hw1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    private Mocks mock;
    private final TextView title;


    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.image_title);
    }

    public void bind(ImageViewMock imageView) {
        title.setText(imageView.getTitle());
    }



    public void setListener(OnItemClickInterface callback) {
        itemView.setOnClickListener(v -> callback.onItemClick(mock));
    }

    public void setMock(Mocks mock) {
        this.mock = mock;
    }
}
