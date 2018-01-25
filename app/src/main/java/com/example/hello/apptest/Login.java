package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText et_id, et_pwd;
    CheckBox auto_login;
    Button btn_help, btn_reg, btn_login;
    String id, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pwd = (EditText) findViewById(R.id.et_pwd1);
        auto_login = (CheckBox) findViewById(R.id.check_auto);
        btn_help = (Button) findViewById(R.id.btn_help);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_reg = (Button) findViewById(R.id.btn_reg);

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Login.this, Help.class);
                startActivity(intent1);
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Login.this, Register.class);
                startActivity(intent2);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {login();}
        });

    }

    public void login(){
        initialize();
        if(!validate()){
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show();
        } else {
            loginSuccess();
        }
    }

    public void initialize(){
        id = et_id.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
    }

    public boolean validate(){
        boolean valid = true;
        if(id.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(id).matches()){
            et_id.setError("ID를 입력하세요");
            valid = false;
        }
        if (pwd.isEmpty()){
            et_pwd.setError("비밀번호를 입력하세요");
            valid = false;
        }
        return valid;
    }

    public void loginSuccess(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", id);
            jsonObject.put("Password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String login_data = jsonObject.toString();
        Toast.makeText(getApplicationContext(), login_data, Toast.LENGTH_LONG).show();
        Intent intent3 = new Intent(Login.this, MoodQ.class);
        startActivity(intent3);
    }

}
