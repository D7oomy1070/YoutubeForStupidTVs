package com.doomy.youtubeforstupidtvs;

import android.os.Handler;
import android.os.HandlerThread;

public class ReciveThreadClass extends HandlerThread {

    private Handler mReciveHandler;
    public ReciveThreadClass(String name) {
        super(name);
    }

    public void postTask(Runnable task){ mReciveHandler.post(task); }

    public void prepareHandler(){
        mReciveHandler = new Handler(getLooper());
    }
}
