package com.example.hello.apptest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by hello on 2018-02-07.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    /*
    * AsyncTask Generic 타입
    * AsyncTask <Params, Progress, Result>
    * Params: doInBackground 파라미터(매개변수) 타입, execute 메소드의 인자 값
    * Progress: doInBackground 작업 시 진행 단위의 타입으로 onProgressUpdate 파라미터 타입
    * Result: doInBackground 리턴값으로 onPostExecute 파라미터 타입
    * 내용 url : https://blog.naver.com/zjawkfsk1117/221194509639
    *           https://blog.naver.com/ljpark6/221206945100
    *
    * 추상클래스: 메소드의 몸체가 없는 빈 껍데기 메소드를 갖는 미완성 클래스
    *   -> 틀(class)이 완성되지 않았음 -> 인스턴스화 불가능
    * 내용 url: https://gudghks0825.blog.me/220074918767
    *
    * */

    Context context;
    AlertDialog alertDialog;
    Boolean connect_ok;
    HttpURLConnection httpURLConnection;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        // 네트워크 통신을 작성하는 공간
        // UI스레드와 상관없이 백그라운드에서 작업을 실행하는 비동기방식으로 작동
        // 비동기방식: 데이터를 주고 받을 때 미리 약속된 신호에 의해 통신하는 방식

        String type = params[0];
        String errorString;
        String register_url = "http:/172.30.1.33/insert_test2.php";

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

                    if (connect_ok == true) {
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
            // AsyncTask로 백그라운드 작업을 실행하기 전에 실행되는 부분
            // 스레드 작업 이전에 수행할 동작을 구현(초기와, 변수 초기화, 네트워크 통신 전 셋팅 할 값 작성)
            // 여기의 경우에는 스레드 작업하기 전 팝업창 구현
         //   alertDialog = new AlertDialog.Builder(context).create();
         //   alertDialog.setTitle("Register Status");
        }

        @Override
        protected void onPostExecute(String result) {
            // doInBackground() 메소드에서 작업이 끝나면 결과 파라미터를 리턴,
            // 그 리턴 값을 통해 스레드 작업이 끝났을 때의 동작 구현
            // 여기서는 결과를 팝업창에 전달해서 출력
            if (connect_ok == true){
                Toast.makeText(context, "서버 연결 성공", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
          //  alertDialog.setMessage(result);
          //  alertDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



