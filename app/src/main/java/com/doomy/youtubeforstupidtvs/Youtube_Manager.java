package com.doomy.youtubeforstupidtvs;

import android.os.Handler;
import android.os.Looper;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class Youtube_Manager extends Thread{
    Handler uiHandler = new Handler(Looper.getMainLooper());
    YouTubePlayer player;
    YouTubePlayerView playerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    String Link;
    Youtube_Manager(YouTubePlayer player, YouTubePlayerView playerView, YouTubePlayer.OnInitializedListener onInitializedListener,String Link){
    this.player = player;
    this.playerView = playerView;
    this.onInitializedListener = onInitializedListener;
    this.Link = Link;

    }
    @Override
    public void run() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        player = youTubePlayer;
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                        youTubePlayer.loadVideo(Link);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };
            playerView.initialize("View",onInitializedListener);

            } // End of Handler Runnable
        }); // End of uiHandler
    }
}
