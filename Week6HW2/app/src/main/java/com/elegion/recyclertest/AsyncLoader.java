package com.elegion.recyclertest;

import android.content.AsyncTaskLoader;
import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class AsyncLoader extends AsyncTaskLoader<String> {

    private String id;

    public AsyncLoader(Context context, String id) {
        super(context);
        this.id = id;
    }

    @Override
    public String loadInBackground() {
        Log.i(AsyncLoader.class.getSimpleName(), "loadInBackground");
        String number = null;
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{id, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null);

        if (cursor != null && cursor.moveToFirst()) {
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();

        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(AsyncLoader.class.getSimpleName(), "onStartLoading");
        forceLoad();
    }

}


