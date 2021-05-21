package com.example.week6hw1;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Mocks> mocks;
    private final int TEXT_VIEW = 1;
    private final int IMAGE_VIEW = 2;


    @Override
    public int getItemViewType(int position) {
        if (mocks.get(position) instanceof TextViewMock){
            return TEXT_VIEW;
        } else {
            return IMAGE_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case (TEXT_VIEW):
                view = inflater.inflate(R.layout.text_view, parent, false);
                holder = new TextViewHolder(view);
                break;
            case (IMAGE_VIEW):
                view = inflater.inflate(R.layout.image_view, parent, false);
                holder = new ImageViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mocks.size();
    }
}
