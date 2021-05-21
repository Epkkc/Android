package com.example.week6hw1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private int count = 0;
    private List<ImageViewMock> mocks = new ArrayList<>(Arrays.asList(new ImageViewMock(count), new ImageViewMock(++count)));

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_view, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(mocks.get(position));

    }

    @Override
    public int getItemCount() {
        return mocks.size();
    }
}
