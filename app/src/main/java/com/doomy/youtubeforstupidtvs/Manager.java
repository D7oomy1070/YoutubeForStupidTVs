package com.doomy.youtubeforstupidtvs;

import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;

public class Manager extends Thread {
    Handler uiHandler= new Handler(Looper.getMainLooper());
    EditText et1;
    Manager(EditText et1){
        this.et1 = et1;
    }

    @Override
    public void run() {
    uiHandler.post(new Runnable() {
        @Override
        public void run() {
            et1.setText("Hello baybe");
        }
    });
    }
}
