package com.example.hello.apptest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.net.URLConnection;

public class MoodQ2 extends AppCompatActivity {

    RadioGroup q1, q2, q3, q4, q5, q6;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12, rb13, rb14, rb15,
            rb16, rb17, rb18, rb19, rb20, rb21, rb22, rb23, rb24, rb25, rb26, rb27, rb28, rb29, rb30;
    Button btn_send;
    int score1 = 0;
    int score2 = 0;
    int score3 = 0;
    int score4 = 0;
    int score5 = 0;
    int score6 = 0;
    boolean isRCheck1 = false;
    boolean isRCheck2 = false;
    boolean isRCheck3 = false;
    boolean isRCheck4 = false;
    boolean isRCheck5 = false;
    boolean isRCheck6 = false;
   // String userscore;
    private BackPressCloseHandler backPressCloseHandler;
    private JSONObject answerJson;
    private int success = 0;
    String username, userId, todayscore;
    int weatherCode = 0;
    String weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_q2);

        btn_send = findViewById(R.id.btn_send);
        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent getData = getIntent();
        username = getData.getStringExtra("name");
        userId = getData.getStringExtra("email");
      //  userscore = getData.getStringExtra("score");
        Log.d("Intent", username);

        q1 = findViewById(R.id.rg1);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        q2 = findViewById(R.id.rg2);
        rb6 = findViewById(R.id.rb6);
        rb7 = findViewById(R.id.rb7);
        rb8 = findViewById(R.id.rb8);
        rb9 = findViewById(R.id.rb9);
        rb10 = findViewById(R.id.rb10);
        q3 = findViewById(R.id.rg3);
        rb11 = findViewById(R.id.rb11);
        rb12 = findViewById(R.id.rb12);
        rb13 = findViewById(R.id.rb13);
        rb14 = findViewById(R.id.rb14);
        rb15 = findViewById(R.id.rb15);
        q4 = findViewById(R.id.rg4);
        rb16 = findViewById(R.id.rb16);
        rb17 = findViewById(R.id.rb17);
        rb18 = findViewById(R.id.rb18);
        rb19 = findViewById(R.id.rb19);
        rb20 = findViewById(R.id.rb20);
        q5 = findViewById(R.id.rg5);
        rb21 = findViewById(R.id.rb21);
        rb22 = findViewById(R.id.rb22);
        rb23 = findViewById(R.id.rb23);
        rb24 = findViewById(R.id.rb24);
        rb25 = findViewById(R.id.rb25);
        q6 = findViewById(R.id.rg6);
        rb26 = findViewById(R.id.rb26);
        rb27 = findViewById(R.id.rb27);
        rb28 = findViewById(R.id.rb28);
        rb29 = findViewById(R.id.rb29);
        rb30 = findViewById(R.id.rb30);
        q_setup_listener();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=result_check();
            }
        });
    }

    public void q_setup_listener() {
        q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        score1 = 1;
                        isRCheck1 = true;
                        break;
                    case R.id.rb2:
                        score1 = 2;
                        isRCheck1 = true;
                        break;
                    case R.id.rb3:
                        score1 = 3;
                        isRCheck1 = true;
                        break;
                    case R.id.rb4:
                        score1 = 4;
                        isRCheck1 = true;
                        break;
                    case R.id.rb5:
                        score1 = 5;
                        isRCheck1 = true;
                        break;
                }
            }

        });

        q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb6:
                        score2 = 1;
                        isRCheck2 = true;
                        break;
                    case R.id.rb7:
                        score2 = 2;
                        isRCheck2 = true;
                        break;
                    case R.id.rb8:
                        score2 = 3;
                        isRCheck2 = true;
                        break;
                    case R.id.rb9:
                        score2 = 4;
                        isRCheck2 = true;
                        break;
                    case R.id.rb10:
                        score2 = 5;
                        isRCheck2 = true;
                        break;
                }
            }
        });

        q3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb11:
                        score3 = 1;
                        isRCheck3 = true;
                        break;
                    case R.id.rb12:
                        score3 = 2;
                        isRCheck3 = true;
                        break;
                    case R.id.rb13:
                        score3 = 3;
                        isRCheck3 = true;
                        break;
                    case R.id.rb14:
                        score3 = 4;
                        isRCheck3 = true;
                        break;
                    case R.id.rb15:
                        score3 = 5;
                        isRCheck3 = true;
                        break;
                }
            }
        });

        // 문제 4,5 6번은 역채점 문항
        q4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb16:
                        score4 = 5;
                        isRCheck4 = true;
                        break;
                    case R.id.rb17:
                        score4 = 4;
                        isRCheck4 = true;
                        break;
                    case R.id.rb18:
                        score4 = 3;
                        isRCheck4 = true;
                        break;
                    case R.id.rb19:
                        score4 = 2;
                        isRCheck4 = true;
                        break;
                    case R.id.rb20:
                        score4 = 1;
                        isRCheck4 = true;
                        break;
                }
            }
        });

        q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb21:
                        score5 = 5;
                        isRCheck5 = true;
                        break;
                    case R.id.rb22:
                        score5 = 4;
                        isRCheck5 = true;
                        break;
                    case R.id.rb23:
                        score5 = 3;
                        isRCheck5 = true;
                        break;
                    case R.id.rb24:
                        score5 = 2;
                        isRCheck5 = true;
                        break;
                    case R.id.rb25:
                        score5 = 1;
                        isRCheck5 = true;
                        break;
                }
            }
        });

        q6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb26:
                        score6 = 5;
                        isRCheck6 = true;
                        break;
                    case R.id.rb27:
                        score6 = 4;
                        isRCheck6 = true;
                        break;
                    case R.id.rb28:
                        score6 = 3;
                        isRCheck6 = true;
                        break;
                    case R.id.rb29:
                        score6 = 2;
                        isRCheck6 = true;
                        break;
                    case R.id.rb30:
                        score6 = 1;
                        isRCheck6 = true;
                        break;

                }
            }
        });
    }

    int result_check() {
        Log.d("result check", Boolean.toString(isRCheck1) + " " +
                Boolean.toString(isRCheck2) + " " +
                Boolean.toString(isRCheck2) + " " +
                Boolean.toString(isRCheck3) + " " +
                Boolean.toString(isRCheck4) + " " +
                Boolean.toString(isRCheck5) + " " +
                Boolean.toString(isRCheck6));

        if (isRCheck1 == false || isRCheck2 == false || isRCheck3 == false || isRCheck4 == false || isRCheck5 == false || isRCheck6 == false) {
            Log.d("issRcheck", "");
            Toast.makeText(getApplicationContext(), "문항에 다 답을 했는지 확인해주세요.", Toast.LENGTH_LONG).show();
            return 0;
        } else {


            RadioButton a1 = findViewById(q1.getCheckedRadioButtonId());

            Log.d("id= ", String.valueOf(q1.getCheckedRadioButtonId()));
            Log.d("a id= ", String.valueOf(a1));
            Log.d("a.text", a1.getText().toString());
            Log.d("score= ", String.valueOf(score1) + ", " + String.valueOf(score2) + ", ");
            Log.d("score= ", String.valueOf(score3) + ", " + String.valueOf(score4) + ", ");
            Log.d("score= ", String.valueOf(score5) + ", " + String.valueOf(score6) + ", ");

            int toScore = score1 + score2 + score3 + score4 + score5 + score6;
            todayscore = Integer.toString(toScore);
            Log.d("totalScore:", todayscore);

            // 음악을 추천해주기 위해서 코드로 따로 분류
            if (toScore >= 22.5 ){
                weatherCode = 1;
            } else if(toScore >= 15){
                weatherCode =2;
            } else if(weatherCode >= 7.5){
                weatherCode = 3;
            } else {
                weatherCode = 4;
            }

             weather = Integer.toString(weatherCode);

            String type = "moodQ";
            BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(this);
            backgroundWorker2.execute(type, userId, todayscore, weather);
            Log.d("userId", userId);

            Intent intent = new Intent(MoodQ2.this, MoodResult1.class);
            intent.putExtra("email", userId);
            intent.putExtra("name", username);
            startActivity(intent);

            return 1;
        }

    }

    @Override
    public void onBackPressed() {       // 종료
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}






