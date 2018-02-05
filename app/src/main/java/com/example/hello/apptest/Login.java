package com.example.hello.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    String JSONTag_id="ID";
    String JSONTag_Passwd="password";
    String intent_mood1 = "LOGIN";
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd1);
        auto_login = findViewById(R.id.check_auto);
        btn_help = findViewById(R.id.btn_help);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);

        backPressCloseHandler = new BackPressCloseHandler(this);

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
            Toast.makeText(this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_LONG).show();
        } else {
//            loginSuccess();
            loginRequest();
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
        else if (pwd.isEmpty()){
            et_pwd.setError("비밀번호를 입력하세요");
            valid = false;
        }
        else if(!id.equals("haku@gmail.com") || !pwd.equals("1234")){
            valid = false;
        }
        return valid;
    }

    public void loginRequest(){
        // Send Json to server
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JSONTag_id,id);
            jsonObject.put(JSONTag_Passwd,pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginResponse(jsonObject);

    }

    public void loginResponse(JSONObject loginRes) {
            // get reply
//        try {
//            String loginResString = loginRes.getString(JSONTag_id);
//            String loginResString1 = loginRes.getString(JSONTag_Passwd);
//            Log.d("JSON id",loginResString);
//            Log.d("JSON pwd",loginResString1);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
            try {
                final Object o = loginRes.get(JSONTag_id);
                final Object o1 = loginRes.get(JSONTag_Passwd);
                Log.d("id",o.toString());
                Log.d("pwd",o1.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
           // 질문 : get과 getString의 차이는 무엇인가요?
           // 차이점 : getString은 변수의 value만 가져오고 get은 변수의 object 전체(value, hashcode, 기타 등등)을 가져온다.
            String login_data = loginRes.toString();
            Log.d("login data",login_data);
            Toast.makeText(getApplication(), login_data, Toast.LENGTH_LONG).show();
            Intent intent3 = new Intent(Login.this, MoodQ.class);
            intent3.putExtra(intent_mood1, login_data);
            startActivity(intent3);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
