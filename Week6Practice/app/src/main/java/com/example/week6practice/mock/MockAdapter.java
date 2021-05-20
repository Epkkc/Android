package com.example.week6practice.mock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week6practice.R;

import java.util.ArrayList;
import java.util.List;

public class MockAdapter extends RecyclerView.Adapter<MockHolder> {

    private List<Mock> mocks = new ArrayList<>();

    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mock_holder, parent, false);


        return new MockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MockHolder holder, int position) {
        holder.bind(mocks.get(position));


    }

    @Override
    public int getItemCount() {
        return mocks.size();
    }

    public void addData(List<Mock> list, boolean refresh){
        if (refresh){
            mocks.clear();

        }
        mocks.addAll(list);
        notifyDataSetChanged();
    }
}
