package com.doomy.youtubeforstupidtvs;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {


    boolean thereIsVideoPlaying = false;

    // ** For YouTube API ** //
    YouTubePlayerView YouTubePlayerView;
    YouTubePlayer.OnInitializedListener OnInitializedListener;
    YouTubePlayer youTubePlayerObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouTubePlayerView = findViewById(R.id.youtubeView);
        /*Manager manager = new Manager(youTubePlayerObject,YouTubePlayerView,OnInitializedListener);
        manager.start();*/

        Listener listener = new Listener(youTubePlayerObject,YouTubePlayerView,OnInitializedListener);
        listener.start();


        // ** Set FullScreen and Landscape Orientation ** //
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ///////////////////////////////////////////////////////

    }

} // End of Class

