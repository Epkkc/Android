package com.example.week4application;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ac_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                showToast(R.string.menu_settings);
                changeFragment(R.id.menu_settings);
                break;
            case R.id.menu_search:
                showToast(R.string.menu_search);
                changeFragment(R.id.menu_search);
                break;
            case R.id.menu_exit:
                showToast(R.string.menu_exit);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(@StringRes int string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    public void changeFragment(@IdRes int id){
        Fragment fragment = null;

        // TODO
        switch (id){
            case  R.id.menu_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.menu_search:
                fragment = new SearchFragment();
                break;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();

    }


}