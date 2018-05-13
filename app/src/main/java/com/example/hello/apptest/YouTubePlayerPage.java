package com.example.hello.apptest;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class YouTubePlayerPage extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener{

    private static final int RECOVERY_DIALOG_REQUEST = 1;   // API 서비스 에러 발생시
    public static final String API_KEY = "AIzaSyAYsmfW7ik6v57oYAx4orJZwlpFGO22pzM";
    private YouTubePlayer player;

    public static String VIDEO_ID = "";
    YouTubePlayerView youTubePlayerView;
    private boolean isFullScreen;
    Intent getIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player_page);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(API_KEY, this);

        getIntent = getIntent();
        VIDEO_ID = getIntent.getStringExtra("video_id");

        layout();   // 화면 정의
    }

    @Override
    protected void onDestroy() {
        if (player != null){
            player.release();
        }
        super.onDestroy();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (null == player)
            return;

        this.player = player;
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);   // 전체화면 세팅
        player.setOnFullscreenListener(this); // 전체화면 리스너 설정

        // 비디오 썸네일 큐
        if (!wasRestored && VIDEO_ID != null){
            player.cueVideo(VIDEO_ID);
        }

        // 동영상 바로 재생
        player.loadVideo(VIDEO_ID);
        player.setPlaybackEventListener(playbackListener);
    }

    // 재생 중 컨트롤 가능한 리스너
    YouTubePlayer.PlaybackEventListener playbackListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean arg0) {

        }

        @Override
        public void onSeekTo(int arg0) {
            player.seekToMillis(arg0);
        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        this.player = null;
        Toast.makeText(this, "Failed to initialize", Toast.LENGTH_LONG).show();
    }

    // 화면이 바뀔 때마다 호출해서 세팅
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        layout();
    }

    @Override
    public void onFullscreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        layout();
    }

    private void layout(){
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        if(isFullScreen){
            youTubePlayerView.setTranslationY(0);
            setLayoutSize(youTubePlayerView, MATCH_PARENT, MATCH_PARENT);
        } else if (isPortrait){
            setLayoutSizeAndGravity(youTubePlayerView, MATCH_PARENT, MATCH_PARENT, Gravity.CENTER_VERTICAL);
        } else {
            youTubePlayerView.setTranslationY(0);
            setLayoutSize(youTubePlayerView, MATCH_PARENT, MATCH_PARENT);
        }

    }

    private static void setLayoutSize(View view, int width, int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private static void setLayoutSizeAndGravity(View view, int width, int height, int gravity){
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        view.setLayoutParams(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST){
            recreate();
        }
    }
}
