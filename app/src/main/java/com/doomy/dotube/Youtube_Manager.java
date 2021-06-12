package com.doomy.dotube;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class Youtube_Manager{

    public YouTubePlayer youTubePlayerObject;
    YouTubePlayerView playerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    Youtube_Manager(YouTubePlayerView playerView, YouTubePlayer.OnInitializedListener onInitializedListener){
    this.playerView = playerView;
    this.onInitializedListener = onInitializedListener;

    }
             void loadVideo(String Link) {
                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayerObject = youTubePlayer;
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                        youTubePlayer.loadVideo(Link);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };
            playerView.initialize("AIzaSyDaP694wCicymhetFVhfubvj_Qvlr5nI-w",onInitializedListener);
    }
            public void releaseVideo(){
            if(youTubePlayerObject != null){ youTubePlayerObject.release(); } }
            // End of releaseVideo Function
            public void pause(){
            if(youTubePlayerObject != null){ youTubePlayerObject.pause(); } }
            // End of pause function
            public void play(){ if(youTubePlayerObject != null){ youTubePlayerObject.play(); } }
            // End of Play Function
            public void seekForward(){ if(youTubePlayerObject != null){ youTubePlayerObject.seekRelativeMillis(10000);} }
            // End of seekForward Function
            public void seekBackward(){ if(youTubePlayerObject != null){ youTubePlayerObject.seekRelativeMillis(-10000);} }
            // End of seekBackward function
}// End of the class
