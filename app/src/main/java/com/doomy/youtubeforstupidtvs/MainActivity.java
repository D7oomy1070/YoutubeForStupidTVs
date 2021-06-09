package com.doomy.youtubeforstupidtvs;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import pl.droidsonroids.gif.GifAnimationMetaData;

public class MainActivity extends YouTubeBaseActivity {


    boolean thereIsVideoPlaying = false;

    // ** For YouTube API ** //
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer youTubePlayerObject = null;

    // ** For Loading Stuff ** //
    View gif; // Loading gif
    TextView textView; // Loading Text



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.youtubeView);
        textView = findViewById(R.id.textView);

        // ** Set FullScreen and Landscape Orientation ** //
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ///////////////////////////////////////////////////////

        // Start waiting for commands
        Recive recive = new Recive(this);
        recive.start();
        gif = findViewById(R.id.gif);// Start loading animation after waiting for commands
        gif.setVisibility(View.VISIBLE);
        ////////////////////////////////////////////////////////
    }

    // Fire it up when there is a command received
    ////////////////////////////////////////////////
    public void TakeAction(String command){
        if(command.equals("clear")){
            gif.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            youTubePlayerView.setVisibility(View.GONE);
            youTubePlayerObject.release();
        }
    }
    public void TakeAction(String command,String link){
        if(command.equals("start")){
            gif.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            youTubePlayerView.setVisibility(View.VISIBLE);
            runVideo(link);


        }
    }
//////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
public void runVideo(String Link){
        Youtube_Manager youtube_manager = new Youtube_Manager(youTubePlayerObject,youTubePlayerView,onInitializedListener,Link);
        youtube_manager.start();
}

} // End of Class

