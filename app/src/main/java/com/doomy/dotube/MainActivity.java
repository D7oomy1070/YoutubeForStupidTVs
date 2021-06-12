package com.doomy.dotube;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {


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
        gif = findViewById(R.id.gif);// Start loading animation after waiting for commands

        // ** Set FullScreen and Landscape Orientation ** //
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ///////////////////////////////////////////////////////

        // ** Start waiting for commands ** //

        Recive recive = new Recive(this);
        recive.start();
        ////////////////////////////////////////////////////////
    }

    // Fire it up when there is a command received
    ////////////////////////////////////////////////
public void uiState(int state){
        switch(state){
            case 0: //There is no video running at the moment
                gif.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                youTubePlayerView.setVisibility(View.GONE);
                break;
            case 1:// There is a video running right now
                gif.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                break;
            case 2:

                break;


        }
}
//////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////


} // End of Class

