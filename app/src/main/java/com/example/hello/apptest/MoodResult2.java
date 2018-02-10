package com.example.hello.apptest;

// 결과화면2에서는 종료 기능을 추가하지 않고 뒤로가기 했을 때 전화면으로 돌아가게 함

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MoodResult2 extends AppCompatActivity {

    String userscore, totalscore, username;
    Intent getIntent;
    TextView tv_result2, tv_music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result2);

        tv_result2 = (TextView) findViewById(R.id.tv_result2);
        tv_music = (TextView) findViewById(R.id.tv_music);

        getIntent = getIntent();
        userscore = getIntent.getStringExtra("score");
        totalscore = getIntent.getStringExtra("moodScore");
        username = getIntent.getStringExtra("name");

        tv_result2.setText(username+"님의 어제 점수는 "+userscore+"점이고, "+"오늘의 점수는 " +totalscore+"점입니다.");


    }
}
