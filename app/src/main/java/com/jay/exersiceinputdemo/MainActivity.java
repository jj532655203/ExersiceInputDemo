package com.jay.exersiceinputdemo;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //activity_main的根布局:题图为背景
    private View container;
    private Rect rect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        container.post(new Runnable() {
            @Override
            public void run() {
                rect = new Rect();
                container.getLocalVisibleRect(rect);
                Log.d(TAG, "container的宽高=" + rect.toString());
            }
        });



    }
}
