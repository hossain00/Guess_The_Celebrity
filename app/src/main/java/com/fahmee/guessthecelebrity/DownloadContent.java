package com.fahmee.guessthecelebrity;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fahmee on 8/1/2016.
 */
public class DownloadContent extends AsyncTask<String, Void, String> {
    String result;
    HttpURLConnection urlConnection;
    URL url;


    @Override
    protected String doInBackground(String... strings) {


        try {
            url = new URL(strings[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data= reader.read();

            while (data!=-1){

                char current = (char)data;

                result += current;

                data = reader.read();

            }

            return result;


        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
