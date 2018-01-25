package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help extends AppCompatActivity {

    Button btn_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btn_main = (Button)findViewById(R.id.btn_main);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
