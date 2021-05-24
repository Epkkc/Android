package com.example.week6hw1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextViewHolder extends RecyclerView.ViewHolder {
    private Mocks mock;
    private final TextView subText;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        subText = itemView.findViewById(R.id.sub_text);
    }

    public void bind(TextViewMock textViewMock) {
        subText.setText(textViewMock.getSubText());
    }

    public void setListener(OnItemClickInterface listener) {
        itemView.setOnClickListener(v -> listener.onItemClick(mock));
    }

    public void setMock(Mocks mock) {
        this.mock = mock;
    }
}
