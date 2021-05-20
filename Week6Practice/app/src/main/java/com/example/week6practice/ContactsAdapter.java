package com.example.week6practice;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week6practice.mock.Mock;
import com.example.week6practice.mock.MockHolder;

public class ContactsAdapter extends RecyclerView.Adapter<MockHolder> {

    private Cursor cursor;
    private onItemClickListener listener;


    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mock_holder, parent, false);


        return new MockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MockHolder holder, int position) {
        if(cursor.moveToPosition(position)){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            holder.bind(new Mock(name, id));
            holder.setListener(listener);
        } else{

        }

    }

    @Override
    public int getItemCount() {
        return cursor!= null ? cursor.getCount() : 0;
    }

    public void swapCursor(Cursor newCursor){
        if (newCursor!=null && newCursor!=cursor){
            if (cursor!=null) cursor.close();
            cursor = newCursor;
            notifyDataSetChanged();
        }


    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener{
        void onItemClick(String id);
    }

}
