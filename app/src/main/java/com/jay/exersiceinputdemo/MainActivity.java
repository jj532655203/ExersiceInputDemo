package com.jay.exersiceinputdemo;

import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jay.exersiceinputdemo.widget.FingerSlideFrameView;

/**
 * desc:默认:题图根布局的背景
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View container;
    private Rect rect;
    private FingerSlideFrameView fingerSlideFrame;
    private int offsetY, offsetX;
    private OnSlideFrameListener mOnSlideFrameListener = new OnSlideFrameListener() {
        @Override
        public void onSlideFrameListener(PointF downPoint, PointF upPoint) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fingerSlideFrame = findViewById(R.id.finger_slide_frame);
        fingerSlideFrame.setOnSlideFrameListener(mOnSlideFrameListener);

        offsetX = fingerSlideFrame.getLeft();
        offsetY = fingerSlideFrame.getTop();
    }
}
