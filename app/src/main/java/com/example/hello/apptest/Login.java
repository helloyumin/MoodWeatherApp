package com.example.hello.apptest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    final String TAG = this.getClass().getName();

    static EditText id, pwd;
    static public boolean login_state = false;
    static CheckBox auto_login;
    static String autoID;
    static String autoPWD;
    static boolean loginChecked;
    Button btn_help, btn_reg, btn_login;
    public static SharedPreferences pref;
    static SharedPreferences.Editor editor;
    private BackPressCloseHandler backPressCloseHandler;
    static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext=this;

        id = findViewById(R.id.et_id);
        pwd = findViewById(R.id.et_pwd1);

        auto_login = findViewById(R.id.check_auto);
        loginChecked = auto_login.isChecked();
        Log.d(TAG, "checkflag: "+ loginChecked);
        auto_login.setOnCheckedChangeListener(this);

        btn_help = findViewById(R.id.btn_help);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);

        btn_reg.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_help.setOnClickListener(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auto_login.isChecked()){
                    savePreference();
                } else {
                    removePreference();
                }

                login_proc(login_state);
            }
        });

        getPreferences();

        if (auto_login.isChecked()){
            login_proc(login_state);
        }

//        pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
//        editor = pref.edit();
//        autoID = pref.getString("username", "");
//        autoPWD = pref.getString("password", "");
//        Log.d(TAG, pref.getString("username", ""));
//        Log.d(TAG, pref.getString("password", ""));
//
//        loginChecked = pref.getBoolean("autoChecked", false);
//        if (loginChecked){
//            id.setText(autoID);
//            pwd.setText(autoPWD);
//            auto_login.setChecked(true);
//        }

        backPressCloseHandler = new BackPressCloseHandler(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (id.getText().toString() != null && pwd.getText().toString() != null) {
                    login_proc(login_state);
                } else
                    Toast.makeText(this, "오류 발생", Toast.LENGTH_LONG).show();
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
        }
    }

    static public void result_login(String result, String pw, String email, String name, int flag) {
        // flag는 오늘 설문지를 했는지 안 했는지 확인하는 용(오늘 점수가 있는지 확인) / 0 = 안 했음, 1 = 했음 100-connection error
        loginMysql.active = false;
        if (result.equals("false")) {
            // id가 없을 때
            Toast.makeText(mContext, "사용자 ID가 없습니다", Toast.LENGTH_LONG).show();
        } else if(result.equals("error")){
            Toast.makeText(mContext, "서버 에러", Toast.LENGTH_LONG).show();
        }
        else {
            if (pw.equals(result)) {
                if( flag == 0 ){ // 오늘 설문지를 안 했을 때
                        Toast.makeText(mContext, name + "님 로그인 되었습니다.", Toast.LENGTH_LONG).show();
                        Intent intent3 = new Intent(mContext, MoodQ.class);
                        intent3.putExtra("name", name);
                        intent3.putExtra("email", email);
                        mContext.startActivity(intent3);
                } else if(flag == 1) { // 오늘 이미 설문지를 했을 때
                        Toast.makeText(mContext, name + "님 로그인 되었습니다.", Toast.LENGTH_LONG).show();
                        Intent intent4 = new Intent(mContext, MoodResult1.class);
                        intent4.putExtra("name", name);
                        intent4.putExtra("email", email);
                        mContext.startActivity((intent4));
                        }
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

    public void getPreferences(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String autoid = preferences.getString("id", "");
        String autopwd = preferences.getString("pwd", "");
        String str_check = preferences.getString("check", "");

        if (!"".equals(autoid) && !"".equals(autopwd)){
            id.setText(autoid);
            pwd.setText(autopwd);
        }

        if ("0".equals(str_check)){
            auto_login.setChecked(true);
        }
    }

    private void savePreference(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id.getText().toString());
        editor.putString("pwd", pwd.getText().toString());

        if (auto_login.isChecked()){
            editor.putString("check", "0");
        }

        editor.commit();
    }

    private void removePreference(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        loginChecked = isChecked;
        Log.d(TAG, "checkFlag1: " + loginChecked);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (auto_login.isChecked()){
//            pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
//            editor = pref.edit();
//
//            editor.putString("username", id.getText().toString());
//            editor.putString("password", pwd.getText().toString());
//            editor.putBoolean("autoChecked", true);
//
//            editor.commit();
//        } else {
//            pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
//            editor = pref.edit();
//            editor.clear();
//            editor.commit();
//        }
//    }

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