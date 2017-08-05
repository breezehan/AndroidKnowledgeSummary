package com.breezehan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author  hands
 * Description
 * Date    2017/7/10 9:45
 * Version
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
////        paint.setStrokeWidth(20);
////        paint.setAntiAlias(true);
////        canvas.drawCircle(300, 300, 200, paint);
//        canvas.drawRect(100,100,500,500,paint);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(700,100,1100,500,paint);

//        paint.setStrokeWidth(20);
//        paint.setStrokeCap(Paint.Cap.ROUND);
////        canvas.drawPoint(50,50,paint);
//        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
//        canvas.drawPoints(points,2,8,paint);
//        paint.setStyle(Paint.Style.FILL);
//        RectF rectF = new RectF(200,100,800,500);
//        canvas.drawArc(rectF,-110,100,true,paint);
//        canvas.drawArc(rectF,20,140,true,paint);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawArc(rectF,180,60,false,paint);
        Path path = new Path();
        path.addArc(200,200,400,400,-225,225);
        path.arcTo(400,200,600,400,-180,225,false);
        path.lineTo(400,542);
        path.close();
//        path.addCircle(300, 300, 200, Path.Direction.CW);

        canvas.drawPath(path,paint);
    }
}
