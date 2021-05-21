package com.example.week6hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickInterface {
    private RecyclerView recycler;
    private TextViewAdapter tvAdapter = new TextViewAdapter();
    private ImageViewAdapter ivAdapter = new ImageViewAdapter();
    private Button tv_btn;
    private Button iv_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));

//        recycler.setAdapter(tvAdapter);
//        tvAdapter.setListener(this);

        recycler.setAdapter(ivAdapter);


        tv_btn = findViewById(R.id.add_tv_element);
        iv_btn = findViewById(R.id.add_iv_element);

        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAdapter.addElement();
            }
        });

    }


    @Override
    public void onItemClick(int pos) {
        tvAdapter.deleteElement(pos);
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }
}