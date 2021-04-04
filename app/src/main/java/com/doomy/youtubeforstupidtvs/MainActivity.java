package com.doomy.youtubeforstupidtvs;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    UDP_Recive udp_recive;
    ReciveThreadClass reciveThreadClass;
    Button bu1;
    MainActivity mainActivity = this;
    boolean thereIsVideoPlaying = false;
    YouTubePlayer youTubePlayer = null;
    EditText et1;
    RunnablesClass runnablesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.view2);
        et1 = (EditText)findViewById(R.id.et1);
        runnablesClass = new RunnablesClass();



        reciveThreadClass = new ReciveThreadClass("ReciveThread");

        Runnable task = new Runnable() {
            @Override
            public void run() {
            try{
                while(true){
                    int port = 1070;
                    String Msg = "";
                    byte[] buffer = new byte[250];

                    DatagramSocket ds = new DatagramSocket(port);
                    DatagramPacket dp = new DatagramPacket(buffer,buffer.length);

                    ds.receive(dp);
                    Msg = new String(buffer, 0,dp.getLength());




                        String finalMsg = Msg;
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


                                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                                        if (!mainActivity.thereIsVideoPlaying) {
                                            youTubePlayer.loadVideo("MV5gmbCFris");
                                            mainActivity.thereIsVideoPlaying = true;
                                            Toast.makeText(getApplicationContext(), "Initialized", Toast.LENGTH_SHORT).show();

                                        }
                                        mainActivity.youTubePlayer = youTubePlayer;
                                    }

                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                                    }
                                };

                                mYouTubePlayerView.initialize("Video", mOnInitializedListener);
                            }
                        });


                    Log.i("Message","Meassge Received: " + Msg);
                    ds.close();
                }

            }catch (IOException e){

            }
            }
        };

        reciveThreadClass.start();
        reciveThreadClass.prepareHandler();
        reciveThreadClass.postTask(task);

    }

    public void BuOnClick(View view){
        Release();
    }

    void Release(){
        if(thereIsVideoPlaying){
            youTubePlayer.release();
            thereIsVideoPlaying = false;
            Toast.makeText(getApplicationContext(),"released",Toast.LENGTH_SHORT).show();

        }
    }
}

