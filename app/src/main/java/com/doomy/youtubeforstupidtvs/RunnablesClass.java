package com.doomy.youtubeforstupidtvs;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class RunnablesClass {
    MainActivity mainActivity;

    RunnablesClass() {
        mainActivity = new MainActivity();
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}