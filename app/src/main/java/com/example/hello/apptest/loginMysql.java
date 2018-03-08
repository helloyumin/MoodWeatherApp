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

/**
 * Created by hello on 2018-02-08.
 */

/*쓰레드: 프로그램을 실행할 때, 프로그램 안에 존재하는 실행 코드(작업)
          메인 쓰레드는 어플을 실행할 때 처음 생기며, 함부로 접근 불가능
          또한 메인 쓰레드에서만 UI 변경 가능
  핸들러: 메인 쓰레드를 함부로 접근 할 수 없기 때문에 서브 쓰레드(본인이 만든 쓰레드, UI 변경 불가능)에서
          핸들러를 통해, 즉 서브 쓰레드가 보낸 메세지를 받아서 UI를 변경
  내용 url: http://itmir.tistory.com/366 */

/*Context: 어떤 액티비티 혹은 어떤 어플리케이션인가에 대해서 구별하는 정보*/

public class loginMysql extends Thread {
    public static boolean active=false;
    public Context mContext;
    Handler mHandler;
    String userId=null,userPw=null,url=null;
    Boolean connect_ok;
    URLApplication urlApplication;
    String login_url;
  //  String login_url="http://172.30.1.102/select_login.php?id=";      //your server IP


    public loginMysql(String id,String pw,Context passed_mContext){
        mHandler=new Handler();
        mContext=passed_mContext;
        urlApplication = (URLApplication)passed_mContext.getApplicationContext();
        login_url = urlApplication.getLoginURL();
        userId = id;
        userPw=pw;
        url=login_url+userId+"&pw="+userPw;
        connect_ok=false;
        // thread를 실행할 때 id와 pw, php의 URL을 넘겨주고 / URL에는 id값을 get방식으로 넘기기 위해 userId를 추가
    }
    /**
     * Calls the <code>run()</code> method of the Runnable object the receiver
     * holds. If no Runnable is set, does nothing.
     * 여기서 runnable은 쓰레드로 인해 실행이 가능한 인터페이스, 러너블을 사용하는 스레드에서 정의 된 run() 추상 메서드를 실행
     * 실행하기 위해서 핸들러로 전달 할 수 있음.
     * runnable은 run() 메서드를 포함할 뿐 아무 작업을 하지 않음.
     *
     * @see Thread#start
     */
    @Override
    public void run() {
        super.run();
        if(active) {
            // 읽어올 데이터를 저장할 StringBuilder를 생성
            // StringBuilder : 문자열 값의 변동이 클 때 유용, String과 다르게 같은 문자열을 갖더라도 변수가 다르면 서로 다른 메모리에 저장
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(url);      // php의 주소와 id가 저장된 URL을 만들어 줌
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();        // Http로 연결 시작
                Log.d("URL= ", url);
                if (conn != null) {
                    conn.setConnectTimeout(1000);
                    conn.setUseCaches(false);

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.d("getresponse code=", String.valueOf(conn.getResponseCode()));
                        // 연결이 성공했을 때
                        connect_ok = true;
                        // BufferReader: 문자/바이트 버퍼 입력, 라인 해석
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while (true) {
                            String line = br.readLine();
                            if (line == null)
                                break;
//                            String line2;
//                            Log.d("buffer ", "[" + line + "]\n");
//                            Log.d("length ", String.valueOf(line.length()));
//                            int q = 0;
//                            for (int i = 0; i < line.length(); i++) {
//                                char c;
//                                c = line.charAt(i);         // BufferReader를 통해 읽어온 문자열(line)을 하나씩 쪼개서 문자로 읽어드림
//                                int ch;
//                                ch = (int) c;      // 읽어온 문자를 다시 아스키 코드로 변환
//                                Log.d(" " + String.valueOf(i) + " ", String.valueOf(line.charAt(i)) + ":" + String.valueOf(ch));
//
//
//                                if (ch > 255) //skip // check hangul data (해결완료)
//                                    q++;
//                            }
                            //  line2 = line.substring(q);
                            // -> 2018/02/21 해결 완료, php 파일을 UTF-8(BOM 없이 저장)로 저장해야 JSON형식 앞에 "ï»¿"가 안 붙음
                            // BOM : Byte Order Mark이라 하고, 인코딩된 문서 첫 머리에 사용하여 정확한 인코딩 방식을 알려주는 역할을 함

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
