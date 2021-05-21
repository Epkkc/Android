package com.example.week6hw1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextViewHolder extends RecyclerView.ViewHolder {

    private TextView subText;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        subText = itemView.findViewById(R.id.sub_text);
    }

    public void bind(TextViewMock textViewMock) {
        subText.setText(textViewMock.getSubText());
    }

    public void setListener(OnItemClickInterface listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(Integer.parseInt(subText.getText().toString()));
            }
        });
    }


}
