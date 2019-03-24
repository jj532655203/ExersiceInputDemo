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

import com.jay.exersiceinputdemo.FingerDrawRectBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jj on 2019/3/23.
 * desc:可手指滑动画出一个矩形框,并记录该框在本view的位置及尺寸
 */

public class FingerDrawRectView extends View {

    private static final String TAG = FingerDrawRectView.class.getSimpleName();
    private static final int DRAW_INTERVAL = 9;
    private static final float CIRCLE_RADIO = 9;
    private Paint paint = new Paint();
    private PointF downPoint;
    private int moveCount;
    private Rect rect;
    private boolean isInside4Circle, isInsideRect;
    private int indexOfInsideCircle = -1;
    private List<FingerDrawRectBean> rectBeans = new ArrayList<>();

    public FingerDrawRectView(Context context) {
        this(context, null);
    }

    public FingerDrawRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FingerDrawRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3F);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint = new PointF(event.getX(), event.getY());
                if (rect != null) {
                    isInside4Circle = isInside4Circle(downPoint);
                    isInsideRect = isInsideRect(downPoint);
                    Log.d(TAG, "是否在圆中?" + isInside4Circle + "//是否在矩形中?" + isInsideRect);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveCount++;
                if (moveCount == DRAW_INTERVAL) {
                    moveCount = 0;
                    drawRect(new PointF(event.getX(), event.getY()));
                }

                break;
            case MotionEvent.ACTION_UP:
                PointF upPoint = new PointF(event.getX(), event.getY());
                drawRect(upPoint);

                isInside4Circle = false;
                isInsideRect = false;
                indexOfInsideCircle = -1;

                break;
            default:
                break;
        }
        return true;
    }

    private void drawRect(PointF upPoint) {
        if (rect == null) {
            generateRect(downPoint, upPoint);
        }
        //拖动4个角的圆圈
        else if (isInside4Circle) {
            if (indexOfInsideCircle < 0) {
                Log.e(TAG, "ontouchEvent ACTION_DOWN逻辑异常");
            } else {
                switch (indexOfInsideCircle) {
                    case 0:
                        rect = new Rect(
                                (int) Math.min(upPoint.x, rect.right),
                                (int) Math.min(upPoint.y, rect.bottom),
                                (int) Math.max(upPoint.x, rect.right),
                                (int) Math.max(upPoint.y, rect.bottom));
                        break;
                    case 1:
                        rect = new Rect(
                                (int) Math.min(upPoint.x, rect.left),
                                (int) Math.min(upPoint.y, rect.bottom),
                                (int) Math.max(upPoint.x, rect.left),
                                (int) Math.max(upPoint.y, rect.bottom));
                        break;
                    case 2:
                        rect = new Rect(
                                (int) Math.min(upPoint.x, rect.right),
                                (int) Math.min(upPoint.y, rect.top),
                                (int) Math.max(upPoint.x, rect.right),
                                (int) Math.max(upPoint.y, rect.top));
                        break;
                    case 3:
                        rect = new Rect(
                                (int) Math.min(upPoint.x, rect.left),
                                (int) Math.min(upPoint.y, rect.top),
                                (int) Math.max(upPoint.x, rect.left),
                                (int) Math.max(upPoint.y, rect.top));
                        break;
                }
            }
        }
        //拖动整个框
        else if (isInsideRect) {
            int width = getWidth();
            int height = getHeight();
            float offSetX = upPoint.x - downPoint.x;
            float offSetY = upPoint.y - downPoint.y;

            //左移出界
            if (rect.left + offSetX < 0) {
                upPoint.x = downPoint.x - rect.left;
            }
            //上移出界
            if (rect.top + offSetY < 0) {
                upPoint.y = downPoint.y - rect.top;
            }
            //右移出界
            if (rect.right + offSetX > width) {
                upPoint.x = width + downPoint.x - rect.right;
            }
            //下移出界
            if (rect.bottom + offSetY > height) {
                upPoint.y = height + downPoint.y - rect.bottom;
            }

            //rect四个点添加offset
            float realOffSetX = upPoint.x - downPoint.x;
            float realOffSetY = upPoint.y - downPoint.y;
            rect.left += realOffSetX;
            rect.top += realOffSetY;
            rect.right += realOffSetX;
            rect.bottom += realOffSetY;
        } else {
            generateRect(downPoint, upPoint);
        }

        invalidate();

        Log.d(TAG, "框的位置:左=" + downPoint.x + "//上=" + downPoint.y + "//右=" + upPoint.x + "//下=" + upPoint.y);
    }

    /**
     * action_down点不在既有的4个圆内,确定是否在矩形框内
     */
    private boolean isInsideRect(PointF downPoint) {
        return downPoint.x > rect.left && downPoint.x < rect.right && downPoint.y > rect.top && downPoint.y < rect.bottom;
    }

    /**
     * 确定action_down的点是否在既有的4个圆内,在的话确定在哪个圆内
     */
    private boolean isInside4Circle(PointF downPoint) {
        float x = downPoint.x;
        float y = downPoint.y;

        //左上点
        if (Math.sqrt((x - rect.left) * (x - rect.left) + (y - rect.top) * (y - rect.top)) < CIRCLE_RADIO) {
            indexOfInsideCircle = 0;

            //右上点
        } else if (Math.sqrt((x - rect.right) * (x - rect.right) + (y - rect.top) * (y - rect.top)) < CIRCLE_RADIO) {
            indexOfInsideCircle = 1;

            //左下点
        } else if (Math.sqrt((x - rect.left) * (x - rect.left) + (y - rect.bottom) * (y - rect.bottom)) < CIRCLE_RADIO) {
            indexOfInsideCircle = 2;

            //右下点
        } else if (Math.sqrt((x - rect.right) * (x - rect.right) + (y - rect.bottom) * (y - rect.bottom)) < CIRCLE_RADIO) {

            indexOfInsideCircle = 3;
        }
        Log.d(TAG, "在哪个圆圈?" + indexOfInsideCircle);
        return indexOfInsideCircle >= 0;
    }

    /**
     * (重)生成框
     */
    private void generateRect(PointF downPoint, PointF movePoint) {
        int minX = (int) Math.min(downPoint.x, movePoint.x);
        int maxX = (int) Math.max(downPoint.x, movePoint.x);
        int minY = (int) Math.min(downPoint.y, movePoint.y);
        int maxY = (int) Math.max(downPoint.y, movePoint.y);
        if (Math.abs(minX - maxX) < CIRCLE_RADIO || Math.abs(minY - maxY) < CIRCLE_RADIO) {
            rect = null;
            return;
        }
        rect = new Rect(minX, minY, maxX, maxY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect == null)
            return;
        canvas.drawRect(rect, paint);
        canvas.drawCircle(rect.left, rect.top, CIRCLE_RADIO, paint);
        canvas.drawCircle(rect.left, rect.bottom, CIRCLE_RADIO, paint);
        canvas.drawCircle(rect.right, rect.top, CIRCLE_RADIO, paint);
        canvas.drawCircle(rect.right, rect.bottom, CIRCLE_RADIO, paint);
        for (FingerDrawRectBean bean  :rectBeans ) {
            Rect _rect = bean.getRect();
            canvas.drawRect(_rect,paint);
            canvas.drawCircle(_rect.left, _rect.top, CIRCLE_RADIO, paint);
            canvas.drawCircle(_rect.left, _rect.bottom, CIRCLE_RADIO, paint);
            canvas.drawCircle(_rect.right, _rect.top, CIRCLE_RADIO, paint);
            canvas.drawCircle(_rect.right, _rect.bottom, CIRCLE_RADIO, paint);
            // TODO: 2019/3/25  绘制文字答案
        }
    }


    public void generateCenterRect() {
        if (rect != null)
            return;
        int width = getWidth();
        int height = getHeight();
        rect = new Rect(width / 2 - 150, height / 2 - 100, width / 2 + 150, height / 2 + 100);
        invalidate();
    }


    private void reInit() {
        downPoint = null;
        moveCount = 0;
        rect = null;
        isInside4Circle = false;
        isInsideRect = false;
        indexOfInsideCircle = -1;
    }

    public void saveARectBean(String answer) {

        rectBeans.add(new FingerDrawRectBean(rect, answer));
        reInit();
    }

    public List<FingerDrawRectBean> getRectBeans() {
        return rectBeans;
    }
}
