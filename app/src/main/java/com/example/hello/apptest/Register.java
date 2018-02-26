package com.example.hello.apptest;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class Register extends AppCompatActivity {

    Button btn_submit, btn_main;
    private EditText email, pwd, name, phone;
    private String str_email, str_pwd, str_name, str_phone;
    private BackPressCloseHandler backPressCloseHandler;
    String testCheck;
    Boolean connect_ok = false;
    String regResult;

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

    private class CheckId extends AsyncTask<String, Void, String>{
        String errorString;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String checkId = "";

            try {
                URL url = new URL(params[0]);
                Log.d("URL", String.valueOf(url));
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                if (httpURLConnection != null){
                    httpURLConnection.setConnectTimeout(1000);
                    httpURLConnection.setUseCaches(false);

                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        connect_ok = true;
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                        // 무한 대기에 걸리는 부분, PHP timeout 설정
                            while (true){
                                String line = br.readLine();
                                if (line == null)
                                    break;

                           // System.out.println("SUCCESS -> " + line);
                            checkId += line;
                            Log.d("LINE", line);
                        }
                        br.close();
                    }
                    httpURLConnection.disconnect();
                } else {
                    connect_ok = false;
                    System.out.println("FAILED");
                }
            } catch (Exception e){
                errorString = e.toString();
                Log.d("Error:", errorString);
                return null;
            }
            return checkId;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result != null){
                Toast.makeText(getApplicationContext(), "서버 연결 성공", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initialize(){
        str_email = email.getText().toString().toLowerCase().trim();
        str_pwd = pwd.getText().toString().trim();
        str_name = name.getText().toString().trim();
        str_phone = phone.getText().toString().trim();
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

    // 질문: 서버는 연결되어있는 상태에서 데이터베이스 서버를 중지시키고 회원가입을 시도하면 "Skipped 245 frames!  The application may be doing too much work on its main thread"가 로그에 뜨는데
    //      이럴 경우 앱에 어떠한 영향을 주고 해결방법에는 무엇이 있을까요?
    public void onReg(View view) {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show();
        } else {
            try {
                CheckId task = new CheckId();
                    testCheck = task.execute("http://172.30.1.102/checkid.php?id=" + str_email).get();
                    Log.d("CheckID: ", testCheck);
                // 아이디 중복 체크
                if (testCheck != null && testCheck.equals("1")) {
                    Toast.makeText(this, "아이디 중복", Toast.LENGTH_LONG).show();
                } else if (testCheck != null && testCheck.equals("0")) {
                    String type = "register";
                    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                    regResult = backgroundWorker.execute(type, str_email, str_pwd, str_name, str_phone).get();
                    Log.d("이메일 소문자", str_email);
                    if (regResult != null) {
                        Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_LONG).show();
                        Intent regIntent = new Intent(Register.this, Login.class);
                        startActivity(regIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplication(), "에러 발생", Toast.LENGTH_LONG);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    @Override
    // 뒤로가기 종료
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
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
