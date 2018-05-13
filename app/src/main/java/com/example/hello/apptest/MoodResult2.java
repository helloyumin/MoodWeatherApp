package com.example.hello.apptest;

// 결과화면2에서는 종료 기능을 추가하지 않고 뒤로가기 했을 때 전화면으로 돌아가게 함
// 어제 점수와 음악 추천 정보 가져옴

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MoodResult2 extends AppCompatActivity {

    private static String TAG = "app_test.MoodResult2";

    private static final String TAG_JSON = "hello";
    private static final String TAG_YSTD = "Score";
    private static final String TAG_MUSIC = "Music";
    private static final String TAG_VIDEO = "Video";

    ArrayList<HashMap<String, String>> mArraylist;
    ListView lv_music;
    String mJsonString;

    URLApplication urlApplication;

    String userscore, todayscore, username, userId, videoId;
    String musicBtn1, musicBtn2, musicBtn3, musicBtn4, musicBtn5;
    String moodResultURL2;
    int flag;
    Intent getIntent;
    TextView tv_result2, tv_result3;
    Button btn_logout;

    Boolean connect_ok;

    BarChart chart;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    BarDataSet barDataSet;
    BarData BARDATA;
    int score1, score2 = 0;     // score1 = 그래프에 표현할 오늘 점수, score2 = 그래프에 표현할 어제 점수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_result2);

        urlApplication = (URLApplication)getApplicationContext();
        moodResultURL2 = urlApplication.getMoodResultURL2();

        btn_logout = (Button)findViewById(R.id.btn_logout2);
        tv_result2 = (TextView) findViewById(R.id.tv_result2);
        tv_result3 = (TextView) findViewById(R.id.tv_result3);
        lv_music = (ListView) findViewById(R.id.lv_music);
        mArraylist = new ArrayList<>();
        tv_result3.setText("음악 목록에 곡을 터치하면 재생");

        getIntent = getIntent();
        todayscore = getIntent.getStringExtra("todayScore");
        userId = getIntent.getStringExtra("email");
        username = getIntent.getStringExtra("name");

        GetScore task = new GetScore();
        task.execute(moodResultURL2 + userId);

        score1 = Integer.parseInt(todayscore);
        Log.d("score1: ", String.valueOf(score1));

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "로그아웃 되셨습니다.", Toast.LENGTH_LONG).show();
                    Intent logoutIntent = new Intent(MoodResult2.this, Login.class);
                    startActivity(logoutIntent);
                }
        });
    }

    private class GetScore extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MoodResult2.this, "잠시만 기다리세요", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - "+result);

            if(result == null){
                Toast.makeText(getApplicationContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
            else{
                mJsonString = result;
                showResult();

                chart = (BarChart)findViewById(R.id.barChart);
                BARENTRY = new ArrayList<>();
                BarEntryLabels = new ArrayList<>();
                AddValuesToBARENTRY();
                AddValuesToBarEntryLabels();
                barDataSet = new BarDataSet(BARENTRY, "어제, 오늘 점수 비교");
                BARDATA = new BarData(BarEntryLabels, barDataSet);
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                YAxis y = chart.getAxisLeft();
                y.setAxisMaxValue(30f);
                y.setAxisMinValue(0f);

                YAxis y1 = chart.getAxisRight();
                y1.setAxisMaxValue(30f);
                y1.setAxisMinValue(0f);

                chart.setData(BARDATA);
                chart.animateY(3000);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            connect_ok = false;
            String serverURL = params[0];
            Log.d("serverURL", serverURL);

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();
                Log.d("responseCode:", String.valueOf(responseCode));

                InputStream inputStream;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connect_ok = true;
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    private void showResult() {
        JSONObject jsonObject = null;
        if (connect_ok = true) {
            try {
                jsonObject = new JSONObject(mJsonString);
                Log.d("mjson:", mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                // 각 비디오ID
                JSONObject jsonMusic1 = jsonArray.getJSONObject(0);
                String jsonMusic1String1 = jsonMusic1.getString("Video");
                musicBtn1 = jsonMusic1String1;
                Log.d("MUSIC1: ", musicBtn1);

                JSONObject jsonMusic2 = jsonArray.getJSONObject(1);
                String jsonMusic1String2 = jsonMusic2.getString("Video");
                musicBtn2 = jsonMusic1String2;
                Log.d("MUSIC2: ", musicBtn2);

                JSONObject jsonMusic3 = jsonArray.getJSONObject(2);
                String jsonMusic1String3 = jsonMusic3.getString("Video");
                musicBtn3 = jsonMusic1String3;
                Log.d("MUSIC3: ", musicBtn3);

                JSONObject jsonMusic4 = jsonArray.getJSONObject(3);
                String jsonMusic1String4 = jsonMusic4.getString("Video");
                musicBtn4 = jsonMusic1String4;
                Log.d("MUSIC4: ", musicBtn4);

                JSONObject jsonMusic5 = jsonArray.getJSONObject(4);
                String jsonMusic1String5 = jsonMusic5.getString("Video");
                musicBtn5 = jsonMusic1String5;
                Log.d("MUSIC5: ", musicBtn5);


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);

                    String score = item.getString(TAG_YSTD);        // 어제 점수 가져오기
                    //Log.d("Score", score);
                    String music = item.getString(TAG_MUSIC);       // 음악 가져오기
                    String VIDEO_ID = item.getString(TAG_VIDEO);    // 유튜브 재생을 위한 비디오ID 가져오기

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(TAG_YSTD, score);
                    hashMap.put(TAG_MUSIC, music);
                    hashMap.put(TAG_VIDEO, VIDEO_ID);

                    userscore = hashMap.get(TAG_YSTD);
                    //Log.d("userscore", userscore);
                    flag = Integer.parseInt(userscore);         // 어제 점수가 없을 경우(no_score = -1) 다르게 문자열을 출력하기 위해서
                   // Log.d("FLAG: ", String.valueOf(flag));
                    score2 = flag;
                    videoId = hashMap.get(TAG_VIDEO);
                    //Log.d("VIDEO_ID", videoId);

                    if (flag == -1) {
                        // 어제의 점수가 없을 경우에는
                        tv_result2.setText(username + "님의 오늘의 점수는 " + todayscore + "점입니다.");

                    } else {
                        tv_result2.setText(username + "님의 어제 점수는 " + userscore + "점이고, " + "오늘의 점수는 " + todayscore + "점입니다.");
                    }

                    mArraylist.add(hashMap);

                }

                SimpleAdapter adapter = new SimpleAdapter(MoodResult2.this, mArraylist, R.layout.music_list,
                        new String[]{TAG_MUSIC},
                        new int[]{R.id.m2});


                lv_music.setAdapter(adapter);
                lv_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                Intent play1 = new Intent(MoodResult2.this, YouTubePlayerPage.class);
                                play1.putExtra("video_id", musicBtn1);
                                startActivity(play1);
                                break;
                            case 1:
                                Intent play2 = new Intent(MoodResult2.this, YouTubePlayerPage.class);
                                play2.putExtra("video_id", musicBtn2);
                                startActivity(play2);
                                break;
                            case 2:
                                Intent play3 = new Intent(MoodResult2.this, YouTubePlayerPage.class);
                                play3.putExtra("video_id", musicBtn3);
                                startActivity(play3);
                                break;
                            case 3:
                                Intent play4 = new Intent(MoodResult2.this, YouTubePlayerPage.class);
                                play4.putExtra("video_id", musicBtn4);
                                startActivity(play4);
                                break;
                            case 4:
                                Intent play5 = new Intent(MoodResult2.this, YouTubePlayerPage.class);
                                play5.putExtra("video_id", musicBtn5);
                                startActivity(play5);
                                break;
                        }
                    }
                });


            } catch (JSONException e) {
                // 서버는 연결 되어있지만 데이터베이스와 연결이 끊겨 있을 때 json형태의 값이 안 넘어와서 나타나는 오류 예외처리
                Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "showResult : ", e);
            }
        } else {
        }
    }

    public void AddValuesToBARENTRY(){
        Log.d("flag", String.valueOf(flag));
        if (flag == -1){
            BARENTRY.add(new BarEntry(score1, 0));
        } else {
            BARENTRY.add(new BarEntry(score1, 0));
            BARENTRY.add(new BarEntry(score2, 1));
            Log.d("SCORE2", String.valueOf(score2));
        }

    }

    public void AddValuesToBarEntryLabels(){
        if (flag == -1){
            BarEntryLabels.add("오늘 점수");
        } else {
            BarEntryLabels.add("오늘 점수");
            BarEntryLabels.add("어제 점수");
        }
    }
}
