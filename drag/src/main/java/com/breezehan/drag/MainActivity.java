package com.breezehan.drag;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DisplayMetrics displayMetrics;
    private float lastX = 0;
    private float lastY = 0;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private boolean isFirst = true;
//    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
        float[] pts = new float[]{0, 50, 80, 100, 400, 300};
        float[] dst = new float[6];
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);
//        Log.i(TAG, "onCreate: " + Arrays.toString(pts));
//        Log.i(TAG, "onCreate: " + Arrays.toString(dst));
//        matrix.mapPoints(dst,0,pts,1,2);
//        Log.i(TAG, "onCreate: 计算后" + Arrays.toString(pts));
//        Log.i(TAG, "onCreate: 计算后" + Arrays.toString(dst));

        float[] src1 = new float[]{1000,800};
        float[] dst1 = new float[2];
        matrix.postTranslate(100, 100);

        matrix.mapVectors(dst1,src1);
        Log.i(TAG, "onCreate: "+Arrays.toString(dst1));

        matrix.mapPoints(dst1, src1);
        Log.i(TAG, "onCreate: "+Arrays.toString(dst1));
    }

    private void initView() {
//        relativeLayout = (RelativeLayout) findViewById(R.id.rl_record);
//        relativeLayout.setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        if (isFirst) {
            // 得到屏幕的宽
            displayMetrics = getResources().getDisplayMetrics();
            screenWidth = displayMetrics.widthPixels;
            // 得到标题栏和状态栏的高度
            Rect rect = new Rect();
            Window window = getWindow();
//            relativeLayout.getWindowVisibleDisplayFrame(rect);
            int statusBarHeight = rect.top;
            int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
            int titleBarHeight = contentViewTop - statusBarHeight;
            // 得到屏幕的高
            screenHeight = displayMetrics.heightPixels - (statusBarHeight + titleBarHeight);
            isFirst = false;
        }
        int action = event.getAction();
        switch (action) {
            //按下
            case MotionEvent.ACTION_DOWN:
                //按下处坐标
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                //移动的距离
                float distanceX = event.getRawX() - lastX;
                float distanceY = event.getRawY() - lastY;
                //移动后控件的坐标
                left = (int) (view.getLeft() + distanceX);
                top = (int) (view.getTop() + distanceY);
                right = (int) (view.getRight() + distanceX);
                bottom = (int) (view.getBottom() + distanceY);
                //处理拖出屏幕的情况
                if (left < 0) {
                    left = 0;
                    right = view.getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = screenWidth - view.getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = view.getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = screenHeight - view.getHeight();
                }
                //显示图片
                view.layout(left, top, right, bottom);
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            //抬起
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return false;
    }
}
