package com.breezehan.window;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "MainActivity";
    private Button mFloatingBtn;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch: ");
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                mWindowManager.updateViewLayout(mFloatingBtn, mLayoutParams);
                break;
        }
        return false;
    }

    public void onButtonClick(View view) {
        initView();
    }

    private void initView() {
        mFloatingBtn = new Button(this);
        mFloatingBtn.setText("Click me");
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        // type 设置 Window 类别（层级）
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mWindowManager.addView(mFloatingBtn, mLayoutParams);
        mFloatingBtn.setOnTouchListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mFloatingBtn);
    }
}
