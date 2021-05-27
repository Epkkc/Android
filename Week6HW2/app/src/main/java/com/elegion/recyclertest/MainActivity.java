package com.elegion.recyclertest;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<String> {

    // добавить фрагмент с recyclerView ---
    // добавить адаптер, холдер и генератор заглушечных данных ---
    // добавить обновление данных и состояние ошибки ---
    // добавить загрузку данных с телефонной книги ---
    // добавить обработку нажатий ---
    // добавить декораторы ---
    public static final String ID_KEY = "ID";
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = getLoaderManager();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance())
                    .commit();
        }
    }


    @Override
    public void onItemClick(String id) {

        Bundle bundle = new Bundle();
        bundle.putString(ID_KEY, id);
        loaderManager.restartLoader(1, bundle, this);
    }


    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        Log.i(MainActivity.class.getSimpleName(), "onCreateLoader");
        return new AsyncLoader(this, bundle.getString(ID_KEY));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        Log.i(MainActivity.class.getSimpleName(), "onLoadFinished");

        if (s != null) {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + s)));
            Log.i(MainActivity.class.getSimpleName(), String.valueOf(loaderManager.getLoader(1).isReset()));
        } else {
            Toast.makeText(this, "There is no such number", Toast.LENGTH_SHORT).show();
        }
        loader.stopLoading();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.i(MainActivity.class.getSimpleName(), "onLoadReset");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_item):
                Loader<Object> loader = loaderManager.getLoader(1);
                loader.cancelLoad();
                Toast.makeText(this, "Запрос отменён", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

