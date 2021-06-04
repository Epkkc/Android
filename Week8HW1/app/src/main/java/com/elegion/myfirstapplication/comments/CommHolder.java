package com.elegion.myfirstapplication.comments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.myfirstapplication.Model.Comment;
import com.elegion.myfirstapplication.Model.Data;
import com.elegion.myfirstapplication.Model.Song;
import com.elegion.myfirstapplication.R;

import java.sql.Date;
import java.sql.Timestamp;

public class CommHolder extends RecyclerView.ViewHolder {

    private TextView mAuthor;
    private TextView mText;
    private TextView mTime;

    public CommHolder(View itemView) {
        super(itemView);
        mAuthor = itemView.findViewById(R.id.comm_author);
        mText = itemView.findViewById(R.id.comm_text);
        mTime = itemView.findViewById(R.id.comm_time);
    }

    public void bind(Comment item) {
        mAuthor.setText(item.getAuthor());
        mText.setText(item.getText());
        Timestamp ts = Timestamp.valueOf(item.getTimestamp().split("T")[0] + " " +  item.getTimestamp().split("T")[1].split("\\+")[0] + ".000");
        String time = "";
        if ((System.currentTimeMillis() - ts.getTime()) > 86_400_000){
            // Комментарий оставлен более чем сутки назад
            time = item.getTimestamp().split("T")[0];
        } else{
            // Комментарий оставлен менее суток назад
            time = item.getTimestamp().split("T")[1].split("\\+")[0];
        }

        mTime.setText(time);
    }


}
