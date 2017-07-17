package com.example.raul_will.travellivesupport;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by raul_Will on 7/17/2017.
 */

public class NetworkUtils extends AppCompatActivity {

    private static String BASE_URL= "https://maps.googleapis.com/maps/api/directions/json";
    private static String ORIGIN= "origin";
    private static String DESTINATION ="destination";
    private static String KEY="key";


    public static URL buildUrl(String source, String destination, Context context){

        Uri builtUri = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(ORIGIN,source).
                appendQueryParameter(DESTINATION, destination).
                appendQueryParameter(KEY,context.getString(R.string.google_map_key)).
                build();

        URL url =null;
        try{
            url= new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(MainActivity.TAG,url.toString());
        return url;
    }

    public static String getResponseFromHttpURL(URL url)throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");
            Boolean hasInput = sc.hasNext();
            if(hasInput){
                return sc.next();
            }else
            {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }
}
