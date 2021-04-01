package com.doomy.youtubeforstupidtvs;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class UDP_Recive
{
    MainActivity mainActivity;
    public String RecivedMSG = "";
    private AsyncTask<Void, Void, Void> async_cient;

    YouTubePlayerView ypv;
    YouTubePlayer.OnInitializedListener ypoi;
    UDP_Recive(YouTubePlayerView ypv, YouTubePlayer.OnInitializedListener ypoi){
    this.ypv = ypv;
    this.ypoi = ypoi;

    }

    public String getRecivedMSG() {
        return RecivedMSG;
    }

    @SuppressLint({"NewApi", "StaticFieldLeak"})
    public void run()
    {
        async_cient = new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                DatagramSocket ds = null;

                try
                {
                    while (true){


                        ds = new DatagramSocket(1070);
                        DatagramPacket dp;
                        byte[] buffer = new byte[250];
                        dp = new DatagramPacket(buffer, buffer.length);
                        //ds.setBroadcast(true);
                        ds.receive(dp);
                        String rMSG = new String(buffer, 0,dp.getLength());
                        Log.i("Message","Meassge Received: " + rMSG);
                        mainActivity = new MainActivity();
                        ypv.initialize(YouTubeConfig.getApiKey(),ypoi);

                        RecivedMSG = rMSG;
                        InetAddress IP =  dp.getAddress();
                        dp = new DatagramPacket(buffer ,buffer.length,IP,1070);
                        ds.send(dp);

                        ds.close();

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (ds != null)
                    {
                        ds.close();
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);
            }
        };

        if (Build.VERSION.SDK_INT >= 11) async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else async_cient.execute();
    }
}