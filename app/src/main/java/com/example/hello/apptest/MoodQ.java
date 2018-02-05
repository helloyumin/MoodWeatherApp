package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MoodQ extends AppCompatActivity {

    String intent_mood1 = "LOGIN";
    String intent_mood2 = "LOGIN_MOOD";
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_q);

        TextView tv_wel = (TextView) findViewById(R.id.tv1);
        Button btn_next = (Button) findViewById(R.id.btn_next);
        backPressCloseHandler = new BackPressCloseHandler(this);
        Intent getData = getIntent();
        JSONObject login = null;
        try {
            login = new JSONObject(getData.getStringExtra(intent_mood1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String loginData = login.toString();


        btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoodQ.this, MoodQ2.class);
                    intent.putExtra(intent_mood2, loginData);
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

