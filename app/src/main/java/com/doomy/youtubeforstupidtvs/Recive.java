package com.doomy.youtubeforstupidtvs;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Recive extends Thread{
    Handler uiHandler= new Handler(Looper.getMainLooper());
    MainActivity mainActivity;
    Recive(MainActivity mainActivity){
        this.mainActivity = mainActivity;
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
                        mainActivity.TakeAction("clear");
                        }else{
                        mainActivity.TakeAction("start",finalMsg);
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
