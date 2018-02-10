package com.example.hello.apptest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hello on 2018-02-09.
 */

public class BackgroundWorker2 extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    BackgroundWorker2(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String moodQ_url = "http://192.168.0.23/insert_score.php";          // 수정 필요

        if (type.equals("moodQ")) {
            try {

                String a1 = params[1];
                String a2 = params[2];
                String a3 = params[3];
                String a4 = params[4];
                String a5 = params[5];
                String a6 = params[6];
                String totalScore = params[7];

                URL url = new URL(moodQ_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_answer = URLEncoder.encode("a1", "UTF-8") + "=" + URLEncoder.encode(a1, "UTF-8")
                        + "&" + URLEncoder.encode("a2", "UTF-8") + "=" + URLEncoder.encode(a2, "UTF-8")
                        + "&" + URLEncoder.encode("a3", "UTF-8") + "=" + URLEncoder.encode(a3, "UTF-8")
                        + "&" + URLEncoder.encode("a4", "UTF-8") + "=" + URLEncoder.encode(a4, "UTF-8")
                        + "&" + URLEncoder.encode("a5", "UTF-8") + "=" + URLEncoder.encode(a5, "UTF-8")
                        + "&" + URLEncoder.encode("a6", "UTF-8") + "=" + URLEncoder.encode(a6, "UTF-8")
                        + "&" + URLEncoder.encode("totalscore", "UTF-8") + "=" + URLEncoder.encode(totalScore, "UTF-8");

                Log.d("POST", post_answer);
               //String post_score = URLEncoder.encode("Score", "UTF-8") + "=" + URLEncoder.encode(totalScore, "UTF-8");
                // Log.d("POST", post_score);

                bufferedWriter.write(post_answer);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                Log.d("result:", result);
                Log.d("line:", line);
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
