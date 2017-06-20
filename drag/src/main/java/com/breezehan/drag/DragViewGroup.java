package com.breezehan.drag;

/**
 * Author  Hands
 * Description
 * Date    2017/6/6 17:40
 * Version
 */
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

public class DragViewGroup extends LinearLayout {
    private static final String TAG = DragViewGroup.class.getSimpleName();
    private int lastX,lastY,screenWidth,screenHeight;
    private boolean isDrag;

    public DragViewGroup(Context context) {
        this(context, null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;//减去下边的高度
        Log.i(TAG, "DragViewGroup: screenHeight:"+screenHeight);
        int scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
/**
 * 获取状态栏高度——方法2
 * */
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        screenHeight -= statusBarHeight2;
        Log.i(TAG, "DragViewGroup: screenHeight-status:"+screenHeight+"\tscaledTouchSlop="+scaledTouchSlop);
    }
    //定位
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //可以在这里确定这个viewGroup的：宽 = r-l.高 = b - t
        Log.i(TAG, "onLayout: ");
    }
    //拦截touch事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG,"onInterceptTouchEvent");
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                isDrag = false;
                lastX = (int) ev.getRawX();//设定移动的初始位置相对位置
                lastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                //event.getRawX()事件点距离屏幕左上角的距离
                int dx = (int) ev.getRawX() - lastX;
                int dy = (int) ev.getRawY() - lastY;

                int left = this.getLeft() + dx;
                int top = this.getTop() + dy;
                int right = this.getRight() + dx;
                int bottom = this.getBottom() + dy;
                if (left < 0) { //最左边
                    left = 0;
                    right = left + this.getWidth();
                }
                if (right > screenWidth) { //最右边
                    right = screenWidth;
                    left = right - this.getWidth();
                }
                if (top < 0) {  //最上边
                    top = 0;
                    bottom = top + this.getHeight();
                }
                if (bottom > screenHeight) {//最下边
                    bottom = screenHeight;
                    top = bottom - this.getHeight();
                }
                this.layout(left, top, right, bottom);//设置控件的新位置
                Log.e(TAG,"position:" + left + ", " + top + ", " + right + ", " + bottom);
                lastX = (int) ev.getRawX();//再次将滑动其实位置定位
                lastY = (int) ev.getRawY();
                isDrag = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isDrag) {
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
