package com.doomy.dotube;

import android.os.*;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Recive extends Thread{
    Handler uiHandler= new Handler(Looper.getMainLooper());
    MainActivity mainActivity;
    Youtube_Manager youtube_manager;
    Recive(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.youtube_manager = new Youtube_Manager(mainActivity.youTubePlayerView, mainActivity.onInitializedListener);
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
                        if (finalMsg.equals("clear")){
                            youtube_manager.releaseVideo();
                            mainActivity.uiState(0);

                        }else if(finalMsg.equals("pause")){
                            youtube_manager.pause();
                        }else if(finalMsg.equals("play")){
                            youtube_manager.play();
                        }else if(finalMsg.equals("seekForward")){
                            youtube_manager.seekForward();
                        }else if (finalMsg.equals("seekBackward")){
                            youtube_manager.seekBackward();
                        }
                        else{
                            youtube_manager.loadVideo(finalMsg);
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
