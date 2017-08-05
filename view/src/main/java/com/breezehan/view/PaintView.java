package com.breezehan.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author  hands
 * Description
 * Date    2017/7/17 13:46
 * Version
 */

public class PaintView extends View{
    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
//        Shader shader = new LinearGradient(100,100,500,500, Color.parseColor("#e91e63"),Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
//        Shader shader = new RadialGradient(300, 300, 100, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
//        paint.setShader(shader);

        ColorFilter lightingColorFileter = new LightingColorFilter(0xffffff, 0x003000);
        Shader shader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.ic_kaiyun), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setColorFilter(lightingColorFileter);
//        paint.setShader(shader);
//        canvas.drawCircle(300,300,200,paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_kaiyun),100,100,paint);
    }
}
