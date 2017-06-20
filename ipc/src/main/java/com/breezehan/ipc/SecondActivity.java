package com.breezehan.ipc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
/**
 * Description
 * Author  Administrator
 * Date    2017/5/23 16:05
 * Version
 */
public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        People people = getIntent().getParcelableExtra("test");
        Log.i(TAG, "onCreate: "+people.getUserName());
    }
}
