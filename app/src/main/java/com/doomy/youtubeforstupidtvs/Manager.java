package com.doomy.youtubeforstupidtvs;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Manager extends Thread {
    Handler uiHandler= new Handler(Looper.getMainLooper());
    Listener listener;
    Manager(YouTubePlayer yp, YouTubePlayerView ypv, YouTubePlayer.OnInitializedListener OnIni){
        Listener listener = new Listener(yp,ypv,OnIni);
        this.listener = listener;
        Log.i("Status","I am the Manager class's Constructor");

    }

    void RunOnUi(){
        listener.start();
    }



    @Override
    public void run() {
    uiHandler.post(new Runnable() {
        @Override
        public void run() {
            RunOnUi();
        }
    });
    }
}
