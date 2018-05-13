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


public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    Boolean connect_ok;
    HttpURLConnection httpURLConnection;
    BackgroundWorker(Context ctx) {
        context = ctx;
    }
    URLApplication urlApplication;

    @Override
    protected String doInBackground(String... params) {

        urlApplication = (URLApplication)context.getApplicationContext();
        String register_url = urlApplication.getRegURL();
        String type = params[0];
        String errorString;

        if (type.equals("register")) {
            try {
                String email = params[1];
                String pwd = params[2];
                String name = params[3];
                String phone = params[4];

                URL url = new URL(register_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8")
                        + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                Log.d("POST", post_data);

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                if (httpURLConnection != null) {
                    httpURLConnection.setConnectTimeout(1000);

                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        connect_ok = true;
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        String result = "";
                        String line = "";

                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();

                        return result;
                    }
                }
                else {
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
            } finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
        }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null){
                Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "서버 연결 성공", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



