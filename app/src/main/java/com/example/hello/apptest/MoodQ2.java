package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MoodQ2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_q2);

        final RadioGroup rg1 = (RadioGroup) findViewById(R.id.q1_rg);
        Button btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.rb1) {
                            Toast.makeText(getApplicationContext(), "1번", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(MoodQ2.this, MoodResult1.class);
                            startActivity(intent1);
                        } else if (checkedId == R.id.rb2) {
                            Toast.makeText(getApplicationContext(), "2번", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(MoodQ2.this, MoodResult1.class);
                            startActivity(intent1);
                        } else if (checkedId == R.id.rb3) {
                            Toast.makeText(getApplicationContext(), "3번", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(MoodQ2.this, MoodResult1.class);
                            startActivity(intent1);
                        }
                    }
                });
            }
        });
    }
}

