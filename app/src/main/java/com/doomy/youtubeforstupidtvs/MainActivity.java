package com.doomy.youtubeforstupidtvs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    UDP_Recive udp_recive;
    Handler UiHandler = new Handler();
    Recive recive;
    Button bu1;
    MainActivity mainActivity = this;
    boolean thereIsVideoPlaying = false;
    YouTubePlayer youTubePlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.view2);




        recive = new Recive("ReciveThread");

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


                    if(Msg == "s") {

                        String finalMsg = Msg;
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


                                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                                        if (!mainActivity.thereIsVideoPlaying) {
                                            youTubePlayer.loadVideo(finalMsg);
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
                    }


                    Log.i("Message","Meassge Received: " + Msg);
                    ds.close();
                }

            }catch (IOException e){

            }
            }
        };

        recive.start();
        recive.prepareHandler();
        recive.postTask(task);

        //udp_recive = new UDP_Recive(mYouTubePlayerView,mOnInitializedListener);
        //udp_recive.run();
        bu1 = (Button) findViewById(R.id.bu1);

        bu1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(mainActivity.thereIsVideoPlaying){
                    youTubePlayer.release();
                    mainActivity.thereIsVideoPlaying = false;
                    Toast.makeText(getApplicationContext(),"released",Toast.LENGTH_SHORT).show();

                }
                //mYouTubePlayerView.initialize("fe",mOnInitializedListener);

            }

        });

        //udp_recive.RecivedMSG;

    }

    void Release(){
        if(thereIsVideoPlaying){
            youTubePlayer.release();
            mYouTubePlayerView.initialize("fe",mOnInitializedListener);
            Toast.makeText(getApplicationContext(),"released",Toast.LENGTH_SHORT).show();
            thereIsVideoPlaying = false;
        }
    }
}

