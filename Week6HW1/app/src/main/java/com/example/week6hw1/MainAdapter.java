package com.example.week6hw1;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int countText = 0;
    private int countImg = 0;
    private ArrayList<Mocks> mocks = new ArrayList<>(Arrays.asList(new TextViewMock(countText++), new ImageViewMock(countImg++), new TextViewMock(countText++)));
    private final int TEXT_VIEW = 1;
    private final int IMAGE_VIEW = 2;
    private OnItemClickInterface callback;
    private RecyclerView recyclerView;

    @Override
    public int getItemViewType(int position) {
        if (mocks.get(position) instanceof TextViewMock) {
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
        switch (viewType) {
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

        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).bind((TextViewMock) mocks.get(position));
            ((TextViewHolder) holder).setMock((TextViewMock) mocks.get(position));
            ((TextViewHolder) holder).setListener(callback);
        } else {
            ((ImageViewHolder) holder).bind((ImageViewMock) mocks.get(position));
            ((ImageViewHolder) holder).setMock((ImageViewMock) mocks.get(position));
            ((ImageViewHolder) holder).setListener(callback);
        }
    }

    @Override
    public int getItemCount() {
        return mocks.size();
    }

    public void addTextElement() {
        mocks.add(new TextViewMock(countText++));
        notifyDataSetChanged();
        recyclerView.scrollToPosition(mocks.size() - 1);
    }

    public void addImageElement() {
        mocks.add(new ImageViewMock(countImg++));
        notifyDataSetChanged();
        recyclerView.scrollToPosition(mocks.size() - 1);
    }

    public void deleteElement(Mocks mock) {
        int pos = mocks.indexOf(mock);
        mocks.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, mocks.size() - 1);
        if (mock instanceof TextViewMock) {
            countText--;
        } else {
            countImg--;
        }
    }

    public void setCallback(OnItemClickInterface callback) {
        this.callback = callback;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setMocks(ArrayList<Mocks> list) {
        this.mocks = list;

    }

    public ArrayList<Mocks> getMocks() {
//        Parcelable[] array = new Parcelable[mocks.size()];
//        for (int i = 0; i < mocks.size(); i++) {
//            array[i] = mocks.get(i).;
//        }
//        return (Parcelable[]) mocks.toArray();
        return mocks;

    }



}
