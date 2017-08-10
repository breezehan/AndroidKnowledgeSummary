package com.breezehan.ipc.bundle;

import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.breezehan.ipc.R;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate: "+getIntent().getStringExtra("info"));
        Bundle extras = getIntent().getExtras();
        Log.d(TAG, "onCreate: "+extras.getString("user","haha"));
    }
}
