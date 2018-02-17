package com.example.hello.apptest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
import java.util.HashMap;

public class MoodResult1 extends AppCompatActivity {

    private static String TAG = "app_test.MoodResult1";

    private static final String TAG_JSON = "hello";
  //  private static final String TAG_EMAIL = "Email2";
  //  private static final String TAG_SCORE = "Score";
    private static final String TAG_WORD = "Word";

    String mJsonString;

    TextView tv_word, tv_score;
    String userscore, word, username, userId, todayscore;
    Intent getResult;
    private BackPressCloseHandler backPressCloseHandler;        // 뒤로가기 2번 누르면 종료

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result1);

        GetScore task = new GetScore();
        task.execute("http://172.30.1.104/select_word.php");

        backPressCloseHandler = new BackPressCloseHandler(this);
        ImageView iv_weather = (ImageView) findViewById(R.id.iv_weather);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_word = (TextView) findViewById(R.id.tv_word);
        Button btn_next = (Button) findViewById(R.id.btn_next);

        getResult = getIntent();
        userscore = getResult.getStringExtra("score");
        todayscore = getResult.getStringExtra("moodScore");
        userId = getResult.getStringExtra("email");
        username = getResult.getStringExtra("name");

        tv_score.setText(username+"님의 점수는 " + todayscore +"점 입니다.");

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodResult1.this, MoodResult2.class);
                intent.putExtra("score", userscore);
                intent.putExtra("moodScore", todayscore);
                intent.putExtra("name", username);
                intent.putExtra("email", userId);
                startActivity(intent);
            }
        });

    }

    private class GetScore extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result == null){
               Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
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

               // String js_email = item.getString(TAG_EMAIL);
              //  String js_score = item.getString(TAG_SCORE);
                String js_word = item.getString(TAG_WORD);

                HashMap<String, String> hashMap = new HashMap<>();

             //   hashMap.put(TAG_EMAIL, js_email);
             //   hashMap.put(TAG_SCORE, js_score);
                hashMap.put(TAG_WORD, js_word);

                word = hashMap.get(TAG_WORD);

                tv_word.setText(word);

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
