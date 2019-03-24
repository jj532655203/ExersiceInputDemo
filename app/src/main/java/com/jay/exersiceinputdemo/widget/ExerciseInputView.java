package com.jay.exersiceinputdemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jay.exersiceinputdemo.OnInputOkListener;
import com.jay.exersiceinputdemo.OnInputResultListener;
import com.jay.exersiceinputdemo.R;

import java.io.File;

/**
 * Created by jj on 2019/3/24.
 */

public class ExerciseInputView extends FrameLayout {

    private static final String TAG = ExerciseInputView.class.getSimpleName();
    private FingerDrawRectView fingerDrawRectView;
    private Button locateRectBtn, enterAnswerBtn, endBtn;
    private ImageView bgPic;
    private OnInputOkListener mOnInputOkListener;

    public ExerciseInputView(@NonNull Context context) {
        this(context, null);
    }

    public ExerciseInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExerciseInputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_exercise_input, this, true);
        initView(view);
        initListener();
    }

    private void initListener() {
        locateRectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                locateRectBtn.setTextColor(Color.RED);
                enterAnswerBtn.setTextColor(Color.BLACK);

                //                fingerDrawRectView正中间生成一个rect
                fingerDrawRectView.generateCenterRect();
            }
        });
        enterAnswerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswerBtn.setTextColor(Color.RED);
                locateRectBtn.setTextColor(Color.BLACK);

                //弹出输入框:输入答案
                InputAlertDialog inputAlertDialog = new InputAlertDialog();
                inputAlertDialog.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), InputAlertDialog.class.getSimpleName());
                inputAlertDialog.setOnInputResultListener(new OnInputResultListener() {
                    @Override
                    public void onInputResult(String answer) {

                        if (!TextUtils.isEmpty(answer)) {

                            //用户点击了确定:保存编辑的rect及答案,去掉步骤2的选中状态
                            fingerDrawRectView.saveARectBean(answer);

                            enterAnswerBtn.setTextColor(Color.BLACK);
                        }
                    }
                });
            }
        });
        endBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnInputOkListener.inputOkListener(fingerDrawRectView.getRectBeans());
                ((AppCompatActivity) getContext()).finish();
            }
        });
    }

    private void initView(View view) {
        fingerDrawRectView = view.findViewById(R.id.draw_rect_view);
        locateRectBtn = view.findViewById(R.id.locate_rect_btn);
        enterAnswerBtn = view.findViewById(R.id.enter_answer_btn);
        endBtn = view.findViewById(R.id.end_btn);
        bgPic = view.findViewById(R.id.exercise_pic);
    }

    public void setExercisePic(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            Log.d(TAG, "setExercisePic 图片路径为空");
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            Log.d(TAG, "setExercisePic 虚图片路径");
            return;
        }
        bgPic.setImageURI(Uri.fromFile(file));
    }

    public void setOnInputOkListener(OnInputOkListener onInputOkListener) {
        mOnInputOkListener = onInputOkListener;
    }

    //    container = findViewById(R.id.container);
    //        container.post(new Runnable() {
    //        @Override
    //        public void run() {
    //            container.getLocalVisibleRect(rect);
    //            offsetX = fingerSlideFrame.getLeft();
    //            offsetY = fingerSlideFrame.getTop();
    //        }
    //    });

    //    downPoint.x += offsetX;
    //    downPoint.y += offsetY;
    //    upPoint.x += offsetX;
    //    upPoint.y += offsetY;
    //
    //    //down点x百分比
    //    float percentDownX = downPoint.x / rect.right;
    //    //down点y百分比
    //    float percentDownY = downPoint.y / rect.bottom;
    //
    //    //up点x百分比
    //    float percentUpX = upPoint.x / rect.right;
    //    //up点y百分比
    //    float percentUpY = upPoint.y / rect.bottom;
}
