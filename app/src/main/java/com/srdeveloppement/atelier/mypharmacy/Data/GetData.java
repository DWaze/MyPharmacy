package com.srdeveloppement.atelier.mypharmacy.Data;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetData {
    String mData;
    String Params;
    public static String LOCALHOST="http://192.168.202.1/";
    public static String appLNG="fr";

    private String LOG_TAG=GetData.class.getSimpleName();


    public String getmData() {
        return mData;
    }


    public GetData(String Params) {
        this.Params=Params;
    }

    protected class DownloadData extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String WebData) {
            mData=WebData;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(LOG_TAG, "the link is:" + params[0]);
            HttpURLConnection httpURLConnection = null;
            try{
                URL url = new URL(params[0]);
                httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setFixedLengthStreamingMode(Params.getBytes().length);
                PrintWriter out = new PrintWriter(httpURLConnection.getOutputStream());
                out.print(Params);
                out.close();
                httpURLConnection.connect();



                int mStatusCode = httpURLConnection.getResponseCode();
                switch (mStatusCode) {
                    case 200:
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        br.close();
                        String result =  sb.toString();
                        return result;
                }
                return "Error connecting to server";
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                return "Error connecting to server";
            } finally {
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }}}
