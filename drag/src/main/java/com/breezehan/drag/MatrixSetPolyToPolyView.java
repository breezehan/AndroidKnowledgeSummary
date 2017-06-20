package com.breezehan.drag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author  Hands
 * Description
 * Date    2017/6/13 15:26
 * Version
 */

public class MatrixSetPolyToPolyView extends View {
    private static final String TAG = "MatrixSetPolyToPolyView";
    private Bitmap mBitmap;             // 要绘制的图片
    private Matrix mPolyMatrix;         // 测试setPolyToPoly用的Matrix

    public MatrixSetPolyToPolyView(Context context) {
        super(context);

        initBitmapAndMatrix();
    }

    public MatrixSetPolyToPolyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBitmapAndMatrix();
    }

    private void initBitmapAndMatrix() {
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.poly_test);
        Log.i(TAG, "initBitmapAndMatrix: ");
        mPolyMatrix = new Matrix();

        float[] src = {0, 0,                                    // 左上
                mBitmap.getWidth(), 0,                          // 右上
                mBitmap.getWidth(), mBitmap.getHeight(),        // 右下
                0, mBitmap.getHeight()};                        // 左下

        float[] dst = {0, 0,                                    // 左上
                mBitmap.getWidth(), 200,                        // 右上
                mBitmap.getWidth(), mBitmap.getHeight() - 100,  // 右下
                0, mBitmap.getHeight()};                        // 左下

        // 核心要点
        mPolyMatrix.setPolyToPoly(src, 0, dst, 0,  4); // src.length >> 1 为位移运算 相当于处以2

        // 此处为了更好的显示对图片进行了等比缩放和平移(图片本身有点大)
//        mPolyMatrix.postScale(0.26f, 0.26f);
//        mPolyMatrix.postTranslate(0, 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 根据Matrix绘制一个变换后的图片
        canvas.drawBitmap(mBitmap, mPolyMatrix, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // ▼ 注意这里使用的是 getAction()，先埋一个小尾巴。
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 手指按下
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起
                break;
            case MotionEvent.ACTION_CANCEL:
                // 事件被拦截
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Log.i(TAG, "onTouchEvent: ");
                // 超出区域
                break;
        }
        return super.onTouchEvent(event);
    }
}
