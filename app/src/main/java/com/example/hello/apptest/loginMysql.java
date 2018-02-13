package com.example.hello.apptest;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hello on 2018-02-08.
 */

public class loginMysql extends Thread {
    public static boolean active=false;
    public Context mContext;
    Handler mHandler;
    String userId=null,userPw=null,url=null;
  //  String login_url="http://192.168.43.130/select_login.php";
    String login_url="http://192.168.0.23/select_login.php?id=";      //your server IP
     // String login_url="http://192.168.43.130/test_login.php";

    public loginMysql(String id,String pw,Context passed_mContext){
        mHandler=new Handler();
        userId=id;
        userPw=pw;
        url=login_url+userId;
        mContext=passed_mContext;
    }

    /**
     * Calls the <code>run()</code> method of the Runnable object the receiver
     * holds. If no Runnable is set, does nothing.
     *
     * @see Thread#start
     */
    @Override
    public void run() {
        Boolean connect_ok=false;
        super.run();
        if(active){
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(url);

                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();
                Log.d("URL= ",url);
                if ( conn != null ) {
                    conn.setConnectTimeout(1000);
                    conn.setUseCaches(false);

                    Log.d("getresponse code=",String.valueOf(conn.getResponseCode()));
                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        connect_ok=true;
                        // BufferReader: 문자/바이트 버퍼 입력, 라인 해석
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;


                            String line2;
                            Log.d("buffer ","["+line+"]\n");
                            Log.d("length ",String.valueOf(line.length()));
                            int q=0;
                            for(int i=0;i<line.length();i++) {
                                char c;
                                c=line.charAt(i);         // BufferReader를 통해 읽어온 문자열(line)을 하나씩 쪼개서 문자로 읽어드림
                                int ch;
                                ch=(int)c;      // 읽어온 문자를 다시 아스키 코드로 변환
                                Log.d(" "+String.valueOf(i)+" ",String.valueOf(line.charAt(i))+":"+String.valueOf(ch));


                          if ( ch > 255 ) //skip
                              q++;
                            }
                            line2=line.substring(q);
                            jsonHtml.append(line2 );       // php를 통해 mysql에서 읽어온 데이터를 라인단위로 저장
                            Log.d("json html",jsonHtml.toString());
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            if ( connect_ok = true)
                show(jsonHtml.toString());      // 읽어온 데이터를 처리하기 위해 show라는 함수로 보냄
            else Toast.makeText(mContext, url+" Connection faild", Toast.LENGTH_LONG).show();

        }



    }

    void show(final String result){
        mHandler.post(new Runnable(){
            @Override
            public void run() {
                try {
                    JSONObject jObject = new JSONObject(result);
                    String getpw =jObject.get("Password").toString();
                    String getid = jObject.get("Email").toString();
                    String getname=jObject.get("Name").toString();
                    String getscore=jObject.get("Score").toString();
                    Log.d("getid", getid);
                    Log.d("getpw",getpw);
                    Log.d("getname",getname);
                    Log.d("getscore",getscore);     // 점수가 존재 했을 때
                    Login.result_login(getpw, userPw, getid, getname, getscore);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Login.result_login("false",  "false", "false", "false", "false");
                }
            }
        });
    }

}
