package com.doomy.youtubeforstupidtvs;

import android.os.Handler;
import android.os.HandlerThread;

public class Recive extends HandlerThread {

    private Handler mReciveHandler;
    public Recive(String name) {
        super(name);
    }

    public void postTask(Runnable task){ mReciveHandler.post(task); }

    public void prepareHandler(){
        mReciveHandler = new Handler(getLooper());
    }
}
