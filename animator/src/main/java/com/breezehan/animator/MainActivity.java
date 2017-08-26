package com.breezehan.animator;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);

    }

    public void showAnimation(View view) {
        Log.d(TAG, "showAnimation: "+view.getTop()+"\tY:"+view.getY()+"\ttranslantionY:"+view.getTranslationY());
        ObjectAnimator.ofFloat(view, "translationY", -view.getHeight()).start();
    }
}
