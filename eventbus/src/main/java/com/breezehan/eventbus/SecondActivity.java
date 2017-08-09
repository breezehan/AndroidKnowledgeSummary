package com.breezehan.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void haha(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new Event("Just do it"));
            }
        }).start();
    }
}
