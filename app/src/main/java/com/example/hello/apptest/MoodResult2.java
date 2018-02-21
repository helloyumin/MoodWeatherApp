package com.example.hello.apptest;

// 결과화면2에서는 종료 기능을 추가하지 않고 뒤로가기 했을 때 전화면으로 돌아가게 함
// 어제 점수와 음악 추천 정보 가져옴

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MoodResult2 extends AppCompatActivity {

    private static String TAG = "app_test.MoodResult2";

    private static final String TAG_JSON = "hello";
    private static final String TAG_YSTD = "Score";
    private static final String TAG_MUSIC = "Music";

    private TextView tv_music;
    ArrayList<HashMap<String, String>> mArraylist;
    ListView lv_music;
    String mJsonString;

    String userscore, todayscore, username, userId;
    int flag;
    Intent getIntent;
    TextView tv_result2;

    Boolean connect_ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result2);

        tv_result2 = (TextView) findViewById(R.id.tv_result2);
        tv_music = (TextView) findViewById(R.id.tv_music);          // 이 텍스트뷰는 삭제예정, 에러 보기 위한 임시 뷰
        lv_music = (ListView) findViewById(R.id.lv_music);
        mArraylist = new ArrayList<>();

        getIntent = getIntent();
        // userscore = getIntent.getStringExtra("ystdScore");
        todayscore = getIntent.getStringExtra("todayScore");
        userId = getIntent.getStringExtra("email");
        username = getIntent.getStringExtra("name");

        GetScore task = new GetScore();
        task.execute("http://172.30.1.33/select_music.php?id=" + userId);

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
                Toast.makeText(getApplicationContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
            else{
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            connect_ok = false;
            String serverURL = params[0];
            Log.d("serverURL", serverURL);

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
                    connect_ok = true;
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
        if (connect_ok = true) {
            try {
                jsonObject = new JSONObject(mJsonString);
                Log.d("mjson:", mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);

                    String score = item.getString(TAG_YSTD);        // 어제 점수 가져오기
                    String music = item.getString(TAG_MUSIC);       // 음악 가져오기

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(TAG_YSTD, score);
                    hashMap.put(TAG_MUSIC, music);

                    userscore = hashMap.get(TAG_YSTD);
                    flag = Integer.parseInt(userscore);         // 어제 점수가 없을 경우(no_score = -1) 다르게 문자열을 출력하기 위해서

                    if (flag == -1) {
                        // 어제의 점수가 없을 경우에는
                        tv_result2.setText(username + "님의 오늘의 점수는 " + todayscore + "점입니다.");
                    } else {
                        tv_result2.setText(username + "님의 어제 점수는 " + userscore + "점이고, " + "오늘의 점수는 " + todayscore + "점입니다.");
                    }

                    mArraylist.add(hashMap);

                }

                ListAdapter adapter = new SimpleAdapter(MoodResult2.this, mArraylist, R.layout.music_list,
                        new String[]{TAG_MUSIC},
                        new int[]{R.id.m2});

                lv_music.setAdapter(adapter);

            } catch (JSONException e) {
                // 서버는 연결 되어있지만 데이터베이스와 연결이 끊겨 있을 때 json형태의 값이 안 넘어와서 나타나는 오류 예외처리
                Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "showResult : ", e);
            }
        } else {
            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
        }
    }
}
