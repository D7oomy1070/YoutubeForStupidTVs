package com.doomy.youtubeforstupidtvs;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        YouTubePlayerView = findViewById(R.id.view2);
        Manager manager = new Manager(youTubePlayerObject,YouTubePlayerView,OnInitializedListener);
        manager.start();


    }

    public void BuOnClick(View view){
        Log.i("Status","Button BuOnClick clicked!!");

        //Release();
    }

    public void BuOnClick2(View view){
        RunTestVideo();

    }

    void Release(){
        if(thereIsVideoPlaying){
            youTubePlayerObject.release();
            thereIsVideoPlaying = false;
            Toast.makeText(getApplicationContext(),"released",Toast.LENGTH_SHORT).show();

        }
    }

    void RunTestVideo(){

        OnInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayerObject = youTubePlayer;
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            if (!thereIsVideoPlaying) {
                youTubePlayer.loadVideo("MV5gmbCFris");
                thereIsVideoPlaying = true;
                //Toast.makeText(getApplicationContext(), "Initialized", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        }
    };
        YouTubePlayerView.initialize("Video", OnInitializedListener);


    } // End of RunTestVideo Func
} // End of Class

