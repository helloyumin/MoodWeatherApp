package com.example.hello.apptest;

import android.content.Intent;
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

public class MoodQ2 extends AppCompatActivity {

    RadioGroup q1, q2, q3, q4, q5, q6;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12, rb13, rb14, rb15,
                rb16, rb17, rb18, rb19, rb20, rb21, rb22, rb23, rb24, rb25, rb26, rb27, rb28, rb29, rb30;
    Button btn_send;
    //int score = 0;
    //int flag = 0;
    //boolean isRCheck = true;
    private BackPressCloseHandler backPressCloseHandler;
    String intent_mood2 = "LOGIN_MOOD";
    String loginRes;
    String moodQ_result = "SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_q2);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent getData = getIntent();
        JSONObject getLogin = null;
        try {
            getLogin = new JSONObject(getData.getStringExtra(intent_mood2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginRes = getLogin.toString();
        Log.d("JSON", loginRes);

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
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginRes != null){
                    result();
                }
            }
        });
    }

    public void result() {

            q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //                   RadioButton rb = (RadioButton) findViewById(checkedId);
                    //                  Toast.makeText(MoodQ2.this, rb.getText() + "체크", Toast.LENGTH_LONG).show();
                    switch (checkedId) {
                        case R.id.rb1:
//                            score = 1;
                            //isRCheck = true;
                            break;
                        case R.id.rb2:
//                            score = 2;
                            //isRCheck = true;
                            break;
                        case R.id.rb3:
                            //score = 3;
                           // isRCheck = true;
                            break;
                        case R.id.rb4:
                            //score = 4;
                            //isRCheck = true;
                            break;
                        case R.id.rb5:
                            //score = 5;
                            //isRCheck = true;
                            break;
                        default:
                            //score = 0;
                            //isRCheck = false;
                            break;
                    }
                    //flag++;
                }

            });

            q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb6:
                            //score = 1;
                            //isRCheck = true;
                            break;
                        case R.id.rb7:
                            //score = 2;
                            //isRCheck = true;
                            break;
                        case R.id.rb8:
                            //score = 3;
                            //isRCheck = true;
                            break;
                        case R.id.rb9:
                            //score = 4;
                            //isRCheck = true;
                            break;
                        case R.id.rb10:
                            //score = 5;
                            //isRCheck = true;
                            break;
                        default:
                            //score = 0;
                            //isRCheck = false;
                            break;
                    }
                    //flag++;
                }
            });

            q3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb11:
                            //score = 1;
                            //isRCheck = true;
                            break;
                        case R.id.rb12:
                            //score = 2;
                            //isRCheck = true;
                            break;
                        case R.id.rb13:
                            //score = 3;
                            //isRCheck = true;
                            break;
                        case R.id.rb14:
                            //score = 4;
                            //isRCheck = true;
                            break;
                        case R.id.rb15:
                            //score = 5;
                            //isRCheck = true;
                            break;
                        default:
                            //score = 0;
                            //isRCheck = false;
                            break;
                    }
                    //flag++;
                }
            });

            // 문제 4,5 6번은 역채점 문항
            q4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb16:
                            //score = 5;
                            //isRCheck = true;
                            break;
                        case R.id.rb17:
                            //score = 4;
                            //isRCheck = true;
                            break;
                        case R.id.rb18:
                            //score = 3;
                            //isRCheck = true;
                            break;
                        case R.id.rb19:
                            //score = 2;
                            //isRCheck = true;
                            break;
                        case R.id.rb20:
                            //score = 1;
                            //isRCheck = true;
                            break;
                        default:
                            //score = 0;
                            //isRCheck = false;
                            break;
                    }
                    //flag++;
                }
            });

            q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb21:
                            //score = 5;
                            //isRCheck = true;
                            break;
                        case R.id.rb22:
                            //score = 4;
                            //isRCheck = true;
                            break;
                        case R.id.rb23:
                            //score = 3;
                            //isRCheck = true;
                            break;
                        case R.id.rb24:
                            //score = 2;
                            //isRCheck = true;
                            break;
                        case R.id.rb25:
                            //score = 1;
                            //isRCheck = true;
                            break;
                        default:
//                            score = 0;
                            //isRCheck = false;
                            break;
                    }
                    //flag++;
                }
            });

            q6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb26:
                            //score = 5;
                            //isRCheck = true;
                            break;
                        case R.id.rb27:
                            //score = 4;
                            //isRCheck = true;
                            break;
                        case R.id.rb28:
                            //score = 3;
                            //isRCheck = true;
                            break;
                        case R.id.rb29:
                            //score = 2;
                           // isRCheck = true;
                            break;
                        case R.id.rb30:
                            //score = 1;
                            //isRCheck = true;
                            break;
                        default:
                            //score = 0;
                            //isRCheck = false;
                            break;
                    }
                    //flag++;
                }
            });

            RadioButton a1 = findViewById(q1.getCheckedRadioButtonId());
            RadioButton a2 = findViewById(q2.getCheckedRadioButtonId());
            RadioButton a3 = findViewById(q3.getCheckedRadioButtonId());
            RadioButton a4 = findViewById(q4.getCheckedRadioButtonId());
            RadioButton a5 = findViewById(q5.getCheckedRadioButtonId());
            RadioButton a6 = findViewById(q6.getCheckedRadioButtonId());

        if (a1 != null && a2 != null && a3 != null && a4 != null && a5 != null && a6 != null) {
                JSONObject moodQAnswer = new JSONObject();
                try {
                    moodQAnswer.put("answer1", a1);
                    moodQAnswer.put("answer2", a2);
                    moodQAnswer.put("answer3", a3);
                    moodQAnswer.put("answer4", a4);
                    moodQAnswer.put("answer5", a5);
                    moodQAnswer.put("answer6", a6);
                }  catch (JSONException e) {
                    e.printStackTrace();
            }
                String answer = moodQAnswer.toString();
                //String answer = a1.getText().toString() + "번, " + a2.getText().toString() + "번, " + a3.getText().toString() + "번, " + a4.getText().toString() + "번, " + a5.getText().toString() + "번, " + a6.getText().toString();
                Intent intent = new Intent(MoodQ2.this, MoodResult1.class);
                intent.putExtra(moodQ_result, answer);
                startActivity(intent);
            } else{
                Toast.makeText(getApplicationContext(), "문항에 다 답을 했는지 확인해주세요.", Toast.LENGTH_LONG).show();
            }
        }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}



