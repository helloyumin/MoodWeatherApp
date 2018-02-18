package com.example.hello.apptest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;


public class Register extends AppCompatActivity {

    Button btn_submit, btn_main;
    private EditText email, pwd, name, phone;
    private String str_email, str_pwd, str_name, str_phone;
    private BackPressCloseHandler backPressCloseHandler;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.et_email);
        pwd = (EditText) findViewById(R.id.et_pwd2);
        name = (EditText) findViewById(R.id.et_name);
        phone = (EditText) findViewById(R.id.et_phone);
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

    }

    public boolean validate(){
        boolean valid = true;
        // 입력을 다 안 했을 경우
        if(str_email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            // 이메일 입력하는 칸이 비었거나 이메일 형식에 안 맞을 때
            email.setError("이메일 주소를 확인하세요");
            valid = false;
        }
        if (str_pwd.isEmpty()){
            pwd.setError("비밀번호를 확인하세요.");
            valid = false;
        }
        if(str_name.isEmpty()){
            name.setError("이름을 확인하세요");
            valid = false;
        }
        if(str_phone.isEmpty() || !Patterns.PHONE.matcher(str_phone).matches()){
            phone.setError("전화번호를 확인하세요");
            valid = false;
        }
        return valid;
    }

    public void initialize(){
        str_email = email.getText().toString().trim();
        str_pwd = pwd.getText().toString().trim();
        str_name = name.getText().toString().trim();
        str_phone = phone.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }


    public void onReg(View view){
        initialize();
        if(!validate()){
            Toast.makeText(this,"회원가입 실패", Toast.LENGTH_LONG).show();
        } else {
            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, str_email, str_pwd, str_name, str_phone);
            Intent regIntent = new Intent(Register.this, Login.class);
            startActivity(regIntent);
        }
    }


//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("user_email", email);
//            obj.put("user_password", pwd);
//            obj.put("user_name", name);
//            obj.put("user_phone", phone);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String regData = obj.toString();
//        Toast.makeText(getApplicationContext(), regData, Toast.LENGTH_LONG).show();

}
