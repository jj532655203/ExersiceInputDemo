package com.jay.exersiceinputdemo;

import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jay.exersiceinputdemo.widget.FingerDrawRectView;

/**
 * desc:默认:题图根布局的背景
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View container;
    private FingerDrawRectView fingerSlideFrame;
    private int offsetY, offsetX;
    private Rect rect = new Rect();
    private OnSlideFrameListener mOnSlideFrameListener = new OnSlideFrameListener() {
        @Override
        public void onSlideFrameListener(PointF downPoint, PointF upPoint) {
            Log.d(TAG, "onSlideFrameListener 绘制了一个框:down点=" + downPoint.toString() + "/up点=" + upPoint.toString());

            downPoint.x += offsetX;
            downPoint.y += offsetY;
            upPoint.x += offsetX;
            upPoint.y += offsetY;

            //down点x百分比
            float percentDownX = downPoint.x / rect.right;
            //down点y百分比
            float percentDownY = downPoint.y / rect.bottom;

            //up点x百分比
            float percentUpX = upPoint.x / rect.right;
            //up点y百分比
            float percentUpY = upPoint.y / rect.bottom;

            Log.d(TAG, "画出来的点:down点x百分比=" + percentDownX + "/down点y百分比=" + percentDownY
                    + "/up点x百分比=" + percentUpX + "/up点y百分比=" + percentUpY);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        fingerSlideFrame = findViewById(R.id.finger_slide_frame);
        fingerSlideFrame.setOnSlideFrameListener(mOnSlideFrameListener);

        container.post(new Runnable() {
            @Override
            public void run() {
                container.getLocalVisibleRect(rect);
                offsetX = fingerSlideFrame.getLeft();
                offsetY = fingerSlideFrame.getTop();
            }
        });
    }
}
