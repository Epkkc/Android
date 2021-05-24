package com.example.week6hw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnItemClickInterface {

    private final MainAdapter mainAdapter = new MainAdapter();

    public static final String MOCK_ARRAY = "MOCK_ARRAY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recycler = findViewById(R.id.recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));

//        recycler.setAdapter(tvAdapter);
//        tvAdapter.setListener(this);

        //recycler.setAdapter(ivAdapter);

        mainAdapter.setCallback(this);
        mainAdapter.setRecyclerView(recycler);

        recycler.setAdapter(mainAdapter);

        Button tv_btn = findViewById(R.id.add_tv_element);
        Button iv_btn = findViewById(R.id.add_iv_element);

        tv_btn.setOnClickListener(v -> mainAdapter.addTextElement());
        iv_btn.setOnClickListener(v -> mainAdapter.addImageElement());

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