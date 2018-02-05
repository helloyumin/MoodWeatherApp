package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    Button btn_submit, btn_main;
    private EditText et_email, et_pwd2, et_name, et_phone;
    private String email, pwd, name, phone;
    private BackPressCloseHandler backPressCloseHandler;
    String regDataSend = "REGDATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd2 = (EditText) findViewById(R.id.et_pwd2);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        backPressCloseHandler = new BackPressCloseHandler(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_main = (Button) findViewById(R.id.btn_main);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Register.this, Login.class);
                startActivity(mainIntent);
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register(){
        initialize();
        if(!validate()){
            Toast.makeText(this,"회원가입 실패", Toast.LENGTH_LONG).show();
        } else {
            onRegSuccess();

        }
    }

    public void onRegSuccess(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_email", email);
            obj.put("user_password", pwd);
            obj.put("user_name", name);
            obj.put("user_phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String regData = obj.toString();
        Toast.makeText(getApplicationContext(), regData, Toast.LENGTH_LONG).show();
        Intent regIntent = new Intent(Register.this, Login.class);
        // regIntent.putExtra(regDataSend, regData);
        startActivity(regIntent);
    }

    public boolean validate(){
        boolean valid = true;
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){    // 이메일 입력하는 칸이 비었거나 이메일 형식에 안 맞을 때
            et_email.setError("이메일 주소를 확인하세요");
            valid = false;
        }
        if (pwd.isEmpty()){
            et_pwd2.setError("비밀번호를 확인하세요.");
            valid = false;
        }
        if(name.isEmpty()){
            et_name.setError("이름을 확인하세요");
            valid = false;
        }
        if(phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()){
            et_phone.setError("전화번호를 확인하세요");
            valid = false;
        }
        return valid;
    }

    public void initialize(){
        email = et_email.getText().toString().trim();
        pwd = et_pwd2.getText().toString().trim();
        name = et_name.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
