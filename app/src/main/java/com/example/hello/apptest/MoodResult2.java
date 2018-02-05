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

    String moodQ_result2= "RESULT2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result2);

        TextView tv_music = (TextView) findViewById(R.id.tv_music);
        Intent getResult2 = getIntent();
        JSONObject result2 = null;
        try {
            result2 = new JSONObject(getResult2.getStringExtra(moodQ_result2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mood_result2 = result2.toString();
        Toast.makeText(getApplicationContext(), mood_result2, Toast.LENGTH_LONG).show();

    }
}
