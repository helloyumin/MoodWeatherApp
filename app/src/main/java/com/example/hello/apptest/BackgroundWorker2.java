package com.example.hello.apptest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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


public class BackgroundWorker2 extends AsyncTask<String, Void, String> {
    Context context;
    Boolean connect_ok;
    URLApplication urlApplication;
    BackgroundWorker2(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String errorString;
        String type = params[0];
        connect_ok = false;
        urlApplication = (URLApplication)context.getApplicationContext();
        String moodQ_url = urlApplication.getMoodQURL();

        if (type.equals("moodQ")) {
            try {
                String email = params[1];
                String score = params[2];
                String weather = params[3];

                URL url = new URL(moodQ_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_answer = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("score", "UTF-8") + "=" + URLEncoder.encode(score, "UTF-8")
                       + "&" + URLEncoder.encode("weather", "UTF-8") + "=" + URLEncoder.encode(weather, "UTF-8");

                Log.d("POST", post_answer);

                bufferedWriter.write(post_answer);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                if (httpURLConnection != null){
                    httpURLConnection.setConnectTimeout(1000);

                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        connect_ok = true;

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

                        return result;
                    }
                    httpURLConnection.disconnect();
                } else {
                    connect_ok = false;
                    System.out.println("FAILED");
                }
            } catch (MalformedURLException e) {
                errorString = e.toString();
                Log.d("MalformedURL Error:", errorString);
                return null;
            } catch (IOException e) {
                errorString = e.toString();
                Log.d("IOException Error:", errorString);
                return null;
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
       super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
       if (connect_ok == true){
           Log.d("서버 연결 상태: ", String.valueOf(connect_ok));
         //  Toast.makeText(context, "서버 연결 성공", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
