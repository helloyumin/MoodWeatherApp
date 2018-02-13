package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MoodQ extends AppCompatActivity {

    String username;
    String userscore;
    String userId;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_q);

        TextView tv_wel = (TextView) findViewById(R.id.tv1);
        Button btn_next = (Button) findViewById(R.id.btn_next);
        Button btn_help = (Button) findViewById(R.id.btn_help);
        backPressCloseHandler = new BackPressCloseHandler(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        userId = intent.getStringExtra("email");
        Log.d("userId", userId);
        userscore = intent.getStringExtra("score");
        Log.d("userscore", userscore);
        tv_wel.setText(username + "님 환영합니다.");
//        Intent getData = getIntent();
//        JSONObject login = null;
//        try {
//            login = new JSONObject(getData.getStringExtra("LOGIN"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        final String loginData = login.toString();

        btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoodQ.this, MoodQ2.class);
                    intent.putExtra("name", username);
                    intent.putExtra("score", userscore);
                    intent.putExtra("email", userId);
                    startActivity(intent);
                }
            });
        }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}

