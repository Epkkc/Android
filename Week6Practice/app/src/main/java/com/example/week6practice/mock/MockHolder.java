package com.example.week6practice.mock;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week6practice.ContactsAdapter;
import com.example.week6practice.R;

import javax.xml.namespace.QName;

public class MockHolder extends RecyclerView.ViewHolder {

    private TextView text;
    private TextView value;
    private String id;

    public MockHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.tv_name);
        value = itemView.findViewById(R.id.tv_value);


    }

    public void bind(Mock mock) {
        text.setText(mock.getName());
        value.setText(mock.getValue());
        id = mock.getValue();
    }

    public void setListener(ContactsAdapter.onItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(id);
            }
        });
    }
}
