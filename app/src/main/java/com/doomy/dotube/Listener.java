package com.doomy.dotube;

import android.os.*;
import android.util.Log;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class Listener extends Thread {
    Handler uiHandler= new Handler(Looper.getMainLooper());
    YouTubePlayer youTubePlayerObject;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener OnInitializedListener;
    MainActivity mainActivity;

    Listener(MainActivity mainActivity){
        this.youTubePlayerObject = mainActivity.youTubePlayerObject;
        this.youTubePlayerView = mainActivity.youTubePlayerView;
        this.OnInitializedListener = mainActivity.onInitializedListener;
        this.mainActivity = mainActivity;
        Log.i("Status","I am the Listener Class's Constructor");
    }



    void RunTest(String Msg){

        OnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayerObject = youTubePlayer;
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                youTubePlayer.loadVideo(Msg);



            }
            // }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize("View",OnInitializedListener);
    }

    @Override
    public void run() {

        try{
            while(true){
                InetAddress ip = InetAddress.getByName("224.1.0.7");
                //InetAddress ip = InetAddress.getByName("localhost");
                int port = 1070;
                String Msg = "";
                byte[] buffer = new byte[250];


                MulticastSocket s = new MulticastSocket(port);
                //DatagramSocket s = new DatagramSocket(port);
                DatagramPacket p = new DatagramPacket(buffer,buffer.length);

                Log.i("Status","Joining The Group!!");

                s.joinGroup(ip);
                Log.i("Status","Joined The Group!!");
                Log.i("Status", String.valueOf(currentThread().getId()));
                s.receive(p);
                s.setSoTimeout(600000);

                Msg = new String(buffer, 0,p.getLength());

                Log.i("Message","Meassge Received: " + Msg);

                String finalMsg = Msg;

                // this code gonna be execute on the UI Looper
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalMsg.equals("clear") && youTubePlayerObject != null){
                            youTubePlayerObject.release();
                            mainActivity.uiState(0);

                        }else{
                            RunTest(finalMsg);
                            mainActivity.uiState(1);
                        }

                    }

                });
                // End of UI Handler

                s.close();
            }

        }catch (IOException e){

        }
    }


}
