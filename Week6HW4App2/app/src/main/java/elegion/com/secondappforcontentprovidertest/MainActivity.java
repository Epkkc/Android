package elegion.com.secondappforcontentprovidertest;

import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText editTextId;
    private Button btn;
    private EditText et1;
    private EditText et2;
    private EditText et3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        editTextId = findViewById(R.id.input_id);
        btn = findViewById(R.id.btn);
        et1 = findViewById(R.id.insert_id);
        et2 = findViewById(R.id.insert_value1);
        et3 = findViewById(R.id.insert_value2);

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                action();
            }
        });

        ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(this, R.array.spinner1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        getSupportLoaderManager().initLoader(12, null, this);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        et1.setHint(R.string.id);
                        et2.setHint(R.string.name);
                        et3.setHint(R.string.release);
                        break;
                    case 1:
                        et1.setHint(R.string.id);
                        et2.setHint(R.string.name);
                        et3.setHint(R.string.duration);
                        break;
                    case 2:
                        et1.setHint(R.string.id);
                        et2.setHint(R.string.album_id);
                        et3.setHint(R.string.song_id);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        // query
                        editTextId.setEnabled(true);
                        et1.setEnabled(false);
                        et2.setEnabled(false);
                        et3.setEnabled(false);
                        break;
                    case 1:
                        // insert
                        editTextId.setEnabled(false);
                        et1.setEnabled(true);
                        et2.setEnabled(true);
                        et3.setEnabled(true);
                        break;
                    case 2:
                        // update
                        editTextId.setEnabled(true);
                        et1.setEnabled(true);
                        et2.setEnabled(true);
                        et3.setEnabled(true);
                        break;
                    case 3:
                        // delete
                        editTextId.setEnabled(true);
                        et1.setEnabled(false);
                        et2.setEnabled(false);
                        et3.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void action() {
        String table = null;
        switch (spinner1.getSelectedItemPosition()) {
            case 0:
                table = "album";
                break;
            case 1:
                table = "song";
                break;
            case 2:
                table = "albumsong";
                break;
        }
        Cursor cursor;
        switch (spinner2.getSelectedItemPosition()) {
            case 0:
                // Выбран Query
                if (TextUtils.isEmpty(editTextId.getText())) {
                    Toast.makeText(this, "Пустое поле", Toast.LENGTH_SHORT).show();
                    cursor = getContentResolver().query(Uri.parse("content://com.elegion.roomdatabase.musicprovider/" + table + "/"), null, null, null);
                    printCursor(cursor);

                } else {
                    Toast.makeText(this, "Не пустое поле " + editTextId.getText().toString(), Toast.LENGTH_SHORT).show();
                    cursor = getContentResolver().query(Uri.parse("content://com.elegion.roomdatabase.musicprovider/" + table + "/" + editTextId.getText().toString()), null, null, null);
                    printCursor(cursor);

                }


                //Toast.makeText(this, "Selected 0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                // Выбран Insert
                ContentValues contentValues = new ContentValues();
                contentValues.put("param1", et1.getText().toString());
                contentValues.put("param2", et2.getText().toString());
                contentValues.put("param3", et3.getText().toString());
                getContentResolver().insert(Uri.parse("content://com.elegion.roomdatabase.musicprovider/" + table + "/"), contentValues);
                //Toast.makeText(this, "Selected 1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                // Выбран Update
                //Toast.makeText(this, "Selected 2", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(editTextId.getText())) {
                    Toast.makeText(this, "Пустое поле id", Toast.LENGTH_SHORT).show();
                } else {
                    // todo НАПИСАТЬ UPDATE
                    ContentValues contentValues1 = new ContentValues();
                    contentValues1.put("param1", et1.getText().toString());
                    contentValues1.put("param2", et2.getText().toString());
                    contentValues1.put("param3", et3.getText().toString());
                    getContentResolver().update(Uri.parse("content://com.elegion.roomdatabase.musicprovider/" + table + "/" + editTextId.getText().toString()), contentValues1, null, null);
                }

                break;
            case 3:
                // Выбран Delete

                //Toast.makeText(this, "Selected 3", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(editTextId.getText())) {
                    Toast.makeText(this, "Пустое поле id", Toast.LENGTH_SHORT).show();
                } else {
                    int i = getContentResolver().delete(Uri.parse("content://com.elegion.roomdatabase.musicprovider/" + table + "/" + editTextId.getText().toString()), null, null);
                    //Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                Uri.parse("content://com.elegion.roomdatabase.musicprovider/song"),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {

            StringBuilder builder = new StringBuilder();
            do {
                builder.append(data.getString(data.getColumnIndex("name"))).append("\n");
            } while (data.moveToNext());
            Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public void printCursor(Cursor data) {
        if (data != null && data.moveToFirst()) {

            StringBuilder builder = new StringBuilder();
            if (data.getColumnIndex("name") != -1) {
                do {
                    builder.append(data.getString(data.getColumnIndex("name"))).append("\n");
                } while (data.moveToNext());
            } else {
                do {
                    builder.append(data.getString(data.getColumnIndex("id"))).append("\n");
                } while (data.moveToNext());
            }

            Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
        }
    }


}
