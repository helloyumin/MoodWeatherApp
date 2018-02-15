package com.example.hello.apptest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

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

public class MoodResult1 extends AppCompatActivity {

    static public boolean result_state = false;
    String userscore, totalscore, username, userId, ystdscore;
    Intent getResult;
    private BackPressCloseHandler backPressCloseHandler;        // 뒤로가기 2번 누르면 종료

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result1);

        backPressCloseHandler = new BackPressCloseHandler(this);
        ImageView iv_weather = (ImageView) findViewById(R.id.iv_weather);
        TextView tv_score = (TextView) findViewById(R.id.tv_score);
        TextView tv_word = (TextView) findViewById(R.id.tv_word);
        Button btn_next = (Button) findViewById(R.id.btn_next);

        getResult = getIntent();
        userscore = getResult.getStringExtra("score");
        userId = getResult.getStringExtra("email");
        totalscore = getResult.getStringExtra("moodScore");
        username = getResult.getStringExtra("name");

        tv_score.setText(username+"님의 점수는 " + totalscore +"점 입니다.");

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodResult1.this, MoodResult2.class);
                intent.putExtra("score", userscore);
                intent.putExtra("moodScore", totalscore);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });

    }

    static public void result_score(String result, String music){

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
