package com.breezehan.drag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * Author  Hands
 * Description
 * Date    2017/6/13 16:38
 * Version
 */

public class MatrixSetRectToRectTest extends View {
    private static final String TAG = "MatrixSetRectToRectTest";
    private Matrix mRectMatrix;
    private Bitmap mBitmap;
    private int mHeight;
    private int mWidth;

    public MatrixSetRectToRectTest(Context context) {
        super(context);
    }

    public MatrixSetRectToRectTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pm25);
        mRectMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] values = new float[9];
        int[] location1 = new int[2];
        Matrix matrix = canvas.getMatrix();
        matrix.getValues(values);
        Log.i(TAG, "location1 = " + Arrays.toString(values));
        location1[0] = (int) values[2];
        location1[1] = (int) values[5];
        Log.i(TAG, "location1 = " + Arrays.toString(location1));

        int[] location2 = new int[2];
        this.getLocationOnScreen(location2);
        Log.i(TAG, "location2 = " + Arrays.toString(location2));

        RectF src = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF dst = new RectF(0, 0, mWidth, mHeight);

        mRectMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
        canvas.drawBitmap(mBitmap,mRectMatrix,new Paint());
    }
}
