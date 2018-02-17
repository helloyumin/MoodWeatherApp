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
        String moodQ_url = "http://172.30.1.104/insert_score.php";

        if (type.equals("moodQ")) {
            try {

//                String a1 = params[1];
//                String a2 = params[2];
//                String a3 = params[3];
//                String a4 = params[4];
//                String a5 = params[5];
//                String a6 = params[6];
                String email = params[1];
                String score = params[2];
                String weather = params[3];

                URL url = new URL(moodQ_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                // 스트림 : 자료의 입출력을 도와주는 중간매개체, 즉 데이터를 읽고 기록하는 중간 역할
                //         단방향이기 때문에 입력, 출력을 하기 위해서는 2개의 스트림이 필요
                // 버퍼 : 데이터를 한 곳에서 다른 한 곳으로 전송하는 동안 일시적으로 그 데이터를 보관하는 메모리의 영역
                // 출력 스트림: 데이터를 보내며 보낸 데이터를 비워버림 -> 출력 스트림에 존재하던 데이터가 목표지점에 도달
                // BufferedWriter:  문자/바이트 스트림에 버퍼 출력, BufferedWriter는 플랫폼에서 사용하는 라인 구분자(line separator) 사용
                // OutputStreamWriter: 문자 스트림을 바이트 스트림으로 변환
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_answer = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("score", "UTF-8") + "=" + URLEncoder.encode(score, "UTF-8")
                       + "&" + URLEncoder.encode("weather", "UTF-8") + "=" + URLEncoder.encode(weather, "UTF-8");
//                        + "&" + URLEncoder.encode("a3", "UTF-8") + "=" + URLEncoder.encode(a3, "UTF-8")
//                        + "&" + URLEncoder.encode("a4", "UTF-8") + "=" + URLEncoder.encode(a4, "UTF-8")
//                        + "&" + URLEncoder.encode("a5", "UTF-8") + "=" + URLEncoder.encode(a5, "UTF-8")
//                        + "&" + URLEncoder.encode("a6", "UTF-8") + "=" + URLEncoder.encode(a6, "UTF-8")
//                        + "&" + URLEncoder.encode("totalscore", "UTF-8") + "=" + URLEncoder.encode(totalScore, "UTF-8");

                Log.d("POST", post_answer);
               //String post_score = URLEncoder.encode("Score", "UTF-8") + "=" + URLEncoder.encode(totalScore, "UTF-8");
                // Log.d("POST", post_score);

                bufferedWriter.write(post_answer);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // 입력 스트림: 데이터를 먼저 스트림으로 읽어드리고 스트림에 존재하는 데이터를 하나씩 읽어 들임.
                // BufferReader: 문자/바이트 버퍼 입력, 라인 해석
                // InputStreamReader: 바이트 스트림을 문자 스트림으로 변환
                InputStream inputStream = httpURLConnection.getInputStream();   // 문자/바이트 입력 스트림을 위한 추상 클래스
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
