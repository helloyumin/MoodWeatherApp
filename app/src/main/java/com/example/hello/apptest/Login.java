package com.example.hello.apptest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.util.Calendar;
import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    static EditText id, pwd;
    static public boolean login_state = false;
    CheckBox auto_login;
    Button btn_help, btn_reg, btn_login;
    Calendar now = Calendar.getInstance();
    static String str_today;

    String JSONTag_id = "ID";
    String JSONTag_Passwd = "password";
    private BackPressCloseHandler backPressCloseHandler;
    static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int today = now.get(Calendar.DATE)-1;
        Log.d("TODAY", String.valueOf(today));
        str_today = Integer.toString(today);
        Log.d("str_today", str_today);
        mContext=this;

        id = findViewById(R.id.et_id);
        pwd = findViewById(R.id.et_pwd1);
        auto_login = findViewById(R.id.check_auto);
        btn_help = findViewById(R.id.btn_help);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);

        btn_reg.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_help.setOnClickListener(this);

        backPressCloseHandler = new BackPressCloseHandler(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (id.getText().toString() != null && pwd.getText().toString() != null) {
                    login_proc(login_state);
                } else
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_help:
                Intent intent1 = new Intent(Login.this, Help.class);
                startActivity(intent1);
                break;
            case R.id.btn_reg:
                Intent intent2 = new Intent(Login.this, Register.class);
                startActivity(intent2);
                break;
        }
    }

    void login_proc(boolean login) { //Login
        if (!login) {
            String str_id = id.getText().toString();
            String str_pwd = pwd.getText().toString();
            loginMysql idchk = new loginMysql(str_id, str_pwd, mContext);
            loginMysql.active = true;
            idchk.start();
        } else {

        }
    }

    static public void result_login(String result, String pwd, String email, String name, String score) {
        loginMysql.active = false;
        if (result.equals("false")) {
            Toast.makeText(mContext, "사용자 ID가 없습니다", Toast.LENGTH_LONG).show();
        } else {
            if (pwd.equals(result)) {
//                if(score == null){
                    Toast.makeText(mContext, name + "님 로그인 되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent3 = new Intent(mContext, MoodQ.class);
                    intent3.putExtra("name", name);
                    intent3.putExtra("email", email);
                    intent3.putExtra("score", score);
                     mContext.startActivity(intent3);
//                } else {
//                    Toast.makeText(mContext, name + "님 로그인 되었습니다.", Toast.LENGTH_LONG).show();
//                    Intent intent4 = new Intent(mContext, MoodResult1.class);
//                    intent4.putExtra("name", name);
//                    intent4.putExtra("email", email);
//                    intent4.putExtra("score", score);
//                    mContext.startActivity((intent4));
//                }
            } else {
                Toast.makeText(mContext, "비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

//    public void loginRequest(){
//        // Send Json to server
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(JSONTag_id,id);
//            jsonObject.put(JSONTag_Passwd,pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        loginResponse(jsonObject);
//
//    }
//
//    public void loginResponse(JSONObject loginRes) {
//            // get reply
////        try {
////            String loginResString = loginRes.getString(JSONTag_id);
////            String loginResString1 = loginRes.getString(JSONTag_Passwd);
////            Log.d("JSON id",loginResString);
////            Log.d("JSON pwd",loginResString1);
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//            try {
//                final Object o = loginRes.get(JSONTag_id);
//                final Object o1 = loginRes.get(JSONTag_Passwd);
//                Log.d("id",o.toString());
//                Log.d("pwd",o1.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//           // 질문 : get과 getString의 차이는 무엇인가요?
//           // 차이점 : getString은 변수의 value만 가져오고 get은 변수의 object 전체(value, hashcode, 기타 등등)을 가져온다.
//            String login_data = loginRes.toString();
//            Log.d("login data",login_data);
//            Toast.makeText(getApplication(), login_data, Toast.LENGTH_LONG).show();
//            Intent intent3 = new Intent(Login.this, MoodQ.class);
//            intent3.putExtra("LOGIN", login_data);
//            startActivity(intent3);
//    }


}