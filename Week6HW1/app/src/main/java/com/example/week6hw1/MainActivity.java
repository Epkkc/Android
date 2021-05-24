package com.example.week6hw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
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
    private MainAdapter mainAdapter = new MainAdapter();

    public static final String MOCK_ARRAY = "MOCK_ARRAY";


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

        //recycler.setAdapter(ivAdapter);

        mainAdapter.setCallback(this);
        mainAdapter.setRecyclerView(recycler);

        recycler.setAdapter(mainAdapter);

        tv_btn = findViewById(R.id.add_tv_element);
        iv_btn = findViewById(R.id.add_iv_element);

        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAdapter.addTextElement();
            }
        });
        iv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAdapter.addImageElement();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(Mocks mock) {
        mainAdapter.deleteElement(mock);
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOCK_ARRAY, mainAdapter.getMocks());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null){
            mainAdapter.setMocks(savedInstanceState.getParcelableArrayList(MOCK_ARRAY));
        }

    }
}