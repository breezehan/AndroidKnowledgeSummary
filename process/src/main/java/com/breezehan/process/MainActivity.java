package com.breezehan.process;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 对应博客http://blog.csdn.net/hds2011/article/details/72663321
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserManager.mUserId = 2;
        Log.i(TAG, "onCreate: mUserId="+UserManager.mUserId);
    }

    public void toSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
