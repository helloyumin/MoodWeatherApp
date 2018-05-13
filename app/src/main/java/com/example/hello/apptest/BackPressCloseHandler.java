package com.example.hello.apptest;

/* 종료를 담당하는 클래스 */

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context){
        this.activity = context;
    }

    public void onBackPressed(){
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // 젤리빈 이후 버전 종료
                activity.finishAffinity();
                toast.cancel();
            }
            else {
                // 젤리빈 이전 버전 종료 방법 찾아보기, 이대로 하면 "뒤로가기"를 눌렀을 때 전 화면으로 돌아감
                activity.finish();
                toast.cancel();
            }
        }
    }

    public void showGuide(){
        toast = Toast.makeText(activity, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
