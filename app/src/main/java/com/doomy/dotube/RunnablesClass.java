package com.doomy.dotube;

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