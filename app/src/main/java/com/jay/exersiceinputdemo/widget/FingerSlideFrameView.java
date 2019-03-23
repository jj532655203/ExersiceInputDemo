package com.jay.exersiceinputdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jj on 2019/3/23.
 * desc:可手指滑动画出一个矩形框,并记录该框在本view的位置及尺寸
 */

public class FingerSlideFrameView extends View {

    private static final String TAG = FingerSlideFrameView.class.getSimpleName();
    private static final int DRAW_INTERVAL = 9;
    private PointF downPoint;
    private int moveCount;
    private Rect rect;
    private Paint paint = new Paint();

    public FingerSlideFrameView(Context context) {
        this(context, null);
    }

    public FingerSlideFrameView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FingerSlideFrameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        Log.d(TAG, "initPaint");
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3F);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                downPoint = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE");
                moveCount++;
                if (moveCount == DRAW_INTERVAL) {
                    moveCount = 0;
                    drawRect(downPoint, new PointF(event.getX(), event.getY()));
                }

                break;
            case MotionEvent.ACTION_UP:
                PointF upPoint = new PointF(event.getX(), event.getY());
                drawRect(downPoint, upPoint);
                Log.d(TAG, "框的位置:左=" + downPoint.x + "//上=" + downPoint.y + "//右=" + upPoint.x + "//下=" + upPoint.y);

                break;
            default:
                break;
        }
        return true;
    }

    private void drawRect(PointF downPoint, PointF movePoint) {
        rect = new Rect((int) downPoint.x, (int) downPoint.y, (int) movePoint.x, (int) movePoint.y);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        if (rect == null)
            return;
        canvas.drawRect(rect, paint);
    }
}
