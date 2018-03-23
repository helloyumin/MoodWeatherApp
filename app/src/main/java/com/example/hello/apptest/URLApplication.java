package com.example.hello.apptest;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by hello on 2018-03-07.
 */

public class URLApplication extends Application {

    private String loginURL = "http://172.30.1.35/select_login.php?id=";
    private String regURL = "http://172.30.1.35/insert_test2.php";
    private String idchkURL = "http://172.30.1.35/checkid.php?id=";
    private String moodQURL = "http://172.30.1.35/insert_score.php";
    private String moodResultURL1 = "http://172.30.1.35/select_word.php?id=";
    private String moodResultURL2 = "http://172.30.1.35/select_music.php?id=";

    public String getLoginURL(){
        return loginURL;
    }

    public void setLoginURL(){
        this.loginURL = loginURL;
    }

    public String getRegURL(){
        return regURL;
    }

    public void setRegURL(){
        this.regURL = regURL;
    }

    public String getIdchkURL(){
        return idchkURL;
    }

    public void setIdchkURL(){
        this.idchkURL = idchkURL;
    }

    public String getMoodQURL() {
        return moodQURL;
    }

    public void setMoodQURL() {
        this.moodQURL = moodQURL;
    }

    public String getMoodResultURL1(){
        return moodResultURL1;
    }

    public void setMoodResultURL1() {
        this.moodResultURL1 = moodResultURL1;
    }

    public String getMoodResultURL2() {
        return moodResultURL2;
    }

    public void setMoodResultURL2() {
        this.moodResultURL2 = moodResultURL2;
    }

    @Override
    public void onCreate() {
        // 앱이 생성될 때 호출, 모든 상태변수와 리소스 초기화 로직을 관리
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 컴포넌트가 실행되는 동안 기기의 설정 정보가 변경될 때 시스템에서 호출
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        // 시스템의 리소스가 부족할 때 발생
        super.onLowMemory();
    }
}
