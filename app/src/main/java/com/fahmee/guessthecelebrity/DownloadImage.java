package com.fahmee.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fahmee on 8/1/2016.
 */
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    Bitmap imageFile;
    HttpURLConnection urlConnection;
    URL url;


    @Override
    protected Bitmap doInBackground(String... urls) {

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            imageFile = BitmapFactory.decodeStream(inputStream);

            return imageFile;



        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
