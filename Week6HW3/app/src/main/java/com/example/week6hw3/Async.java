//package com.example.week6hw3;
//
//import android.app.DownloadManager;
//import android.content.Context;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Environment;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.loader.content.AsyncTaskLoader;
//
//public class Async extends AsyncTaskLoader<Integer> {
//
//    private String URL;
//
//    public Async(@NonNull Context context, String URL) {
//        super(context);
//        this.URL = URL;
//    }
//
//    @Nullable
//    @Override
//    public Integer loadInBackground() {
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//        request.setAllowedOverRoaming(false);
//        request.setTitle("Picture");
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/GadgetSaint/"  + "/" + "Sample" + ".png");
//
//
//        refid = downloadManager.enqueue(request);
//        return null;
//    }
//}
