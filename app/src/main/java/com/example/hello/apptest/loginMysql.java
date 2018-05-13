package com.example.hello.apptest;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class loginMysql extends Thread {
    public static boolean active=false;
    public Context mContext;
    Handler mHandler;
    String userId=null,userPw=null,url=null;
    Boolean connect_ok;
    URLApplication urlApplication;
    String login_url;

    public loginMysql(String id,String pw,Context passed_mContext){
        mHandler=new Handler();
        mContext=passed_mContext;
        urlApplication = (URLApplication)passed_mContext.getApplicationContext();
        login_url = urlApplication.getLoginURL();
        userId = id;
        userPw=pw;
        url=login_url+userId+"&pw="+userPw;
        connect_ok=false;
    }

    @Override
    public void run() {
        super.run();
        if(active) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();        // Http로 연결 시작
                Log.d("URL= ", url);
                if (conn != null) {
                    conn.setConnectTimeout(1000);
                    conn.setUseCaches(false);

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.d("getresponse code=", String.valueOf(conn.getResponseCode()));
                        // 연결이 성공했을 때
                        connect_ok = true;
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while (true) {
                            String line = br.readLine();
                            if (line == null)
                                break;


                            jsonHtml.append(line + "\n");       // php를 통해 mysql에서 읽어온 데이터를 라인단위로 저장
                            Log.d("json html", jsonHtml.toString());
                        }
                        br.close();
                    }
                    conn.disconnect();      // 연결을 끊음
                } else {
                    System.out.println("FAILED");
                }
            } catch (Exception e) {
                e.printStackTrace();
                // failure code
            }
            if (connect_ok == true) {
                show(jsonHtml.toString());
            }     // 읽어온 데이터를 처리하기 위해 show라는 함수로 보냄
            else
            {  mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, url+" Connection fail", Toast.LENGTH_LONG).show();
                }
            });}
             }
        }

    void show(final String result){     // 읽어 온 데이터를 처리하는 함수
        mHandler.post(new Runnable(){
            @Override
            public void run() {
                if ( connect_ok==true ) {
                    try {
                        JSONObject jObject = new JSONObject(result);
                        String getpw = jObject.get("Password").toString();
                        String getid = jObject.get("Email").toString();
                        String getname = jObject.get("Name").toString();
                        Integer getflag = Integer.valueOf(jObject.get("flag").toString());

                        Log.d("getid", getid);
                        Log.d("getpw", getpw);
                        Log.d("getname", getname);
                        Log.d("getflag", String.valueOf(getflag));

                        Login.result_login(getpw, userPw, getid, getname, getflag);
                        // 값을 비교해서 처리하기 위하여 Login 액티비티로 전달
                    } catch (JSONException e) {
                        e.printStackTrace();
                     //   Toast.makeText(mContext, "에러 발생", Toast.LENGTH_LONG).show();
                        Login.result_login("false", "false", "false", "false", 100);
                    }
                }else{
                    Login.result_login("error", "false", "false", "false", 100);
                }
            }
        });
    }

}
