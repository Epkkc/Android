package com.example.week6hw1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextViewAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private OnItemClickInterface listener;
    private int count = 0;
    private List<TextViewMock> mocks = new ArrayList<>(Arrays.asList(new TextViewMock(count), new TextViewMock(++count)));


    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.text_view, parent, false);

        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.bind(mocks.get(position));
        holder.setListener(listener);

    }

    @Override
    public int getItemCount() {
        return mocks.size();
    }

    public void addElement() {
        count++;
        mocks.add(new TextViewMock(count));
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickInterface listener) {
        this.listener = listener;
    }

    public void deleteElement(Mocks mock) {
        int pos = mocks.indexOf(mock);
        mocks.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, mocks.size() - 1);
        count--;
        //notifyDataSetChanged();
    }
}
