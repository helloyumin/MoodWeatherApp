package com.example.hello.apptest;

// 결과화면2에서는 종료 기능을 추가하지 않고 뒤로가기 했을 때 전화면으로 돌아가게 함

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MoodResult2 extends AppCompatActivity {

    private static String TAG = "app_test.MoodResult2";

    private static final String TAG_JSON = "hello";
    private static final String TAG_NUM = "Num";
    private static final String TAG_MUSIC = "Music";

    private TextView tv_music;
    ArrayList<HashMap<String, String>> mArraylist;
    ListView lv_music;
    String mJsonString;

    String userscore, totalscore, username;
    Intent getIntent;
    TextView tv_result2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result2);

        tv_result2 = (TextView) findViewById(R.id.tv_result2);
        tv_music = (TextView) findViewById(R.id.tv_music);
        lv_music = (ListView) findViewById(R.id.lv_music);
        mArraylist = new ArrayList<>();

        GetScore task = new GetScore();
        task.execute("http://192.168.0.23/select_music.php");

        getIntent = getIntent();
        userscore = getIntent.getStringExtra("score");
        totalscore = getIntent.getStringExtra("moodScore");
        username = getIntent.getStringExtra("name");

        tv_result2.setText(username + "님의 어제 점수는 " + userscore + "점이고, " + "오늘의 점수는 " + totalscore + "점입니다.");

    }

    private class GetScore extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MoodResult2.this, "잠시만 기다리세요", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            tv_music.setText(result);
            Log.d(TAG, "response - "+result);

            if(result == null){
                tv_music.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();
                Log.d("responseCode:", String.valueOf(responseCode));

                InputStream inputStream;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    private void showResult() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mJsonString);

            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String num = item.getString(TAG_NUM);
                String music = item.getString(TAG_MUSIC);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_NUM, num);
                hashMap.put(TAG_MUSIC, music);

                mArraylist.add(hashMap);

            }

            ListAdapter adapter = new SimpleAdapter(MoodResult2.this, mArraylist, R.layout.music_list,
                    new String[]{TAG_NUM, TAG_MUSIC},
                    new int[]{R.id.m1, R.id.m2});

            lv_music.setAdapter(adapter);

        } catch (JSONException e) {
            Log.d(TAG, "showrResult : ", e);
        }
    }
}
