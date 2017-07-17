package com.example.raul_will.travellivesupport;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mSource, mDestination;
    TextView mErrorMsg;
    Button mSubmit;
    ProgressBar mProgressBar;
    static String TAG= "Main";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext=this;
        mSource = (EditText) findViewById(R.id.source);
        mDestination = (EditText) findViewById(R.id.destination);
        mErrorMsg = (TextView) findViewById(R.id.errorText);
        mSubmit = (Button) findViewById(R.id.submit_id);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void submit(View v){

        URL url= NetworkUtils.buildUrl(
                mSource.getText().toString(),
                mDestination.getText().toString(),
                this);

        new FetchData().execute(url);
    }

    private void clearScreen(){
        mErrorMsg.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage(){
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    public class FetchData extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            clearScreen();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String jsonData = null;
            try {
                jsonData = NetworkUtils.getResponseFromHttpURL(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(String jsonData) {

            mProgressBar.setVisibility(View.INVISIBLE);
            // Log.d(tag,""+s);
            if(jsonData!= null && jsonData!="") {
                Log.d(TAG,"JsonData ->\n "+jsonData);
            }else{
                showErrorMessage();
            }
        }
    }

}
