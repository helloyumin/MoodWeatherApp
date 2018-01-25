package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MoodResult1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result1);

        ImageView iv_weather = (ImageView) findViewById(R.id.iv_weather);
        TextView tv_score = (TextView) findViewById(R.id.tv_score);
        TextView tv_word = (TextView) findViewById(R.id.tv_word);
        Button btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodResult1.this, MoodResult2.class);
                startActivity(intent);
            }
        });

    }
}
