package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoodResult2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result2);

        TextView tv_music = (TextView) findViewById(R.id.tv_music);
        Button btn_before = (Button) findViewById(R.id.btn_before);

        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodResult2.this, MoodResult1.class);
                startActivity(intent);
            }
        });
    }
}
