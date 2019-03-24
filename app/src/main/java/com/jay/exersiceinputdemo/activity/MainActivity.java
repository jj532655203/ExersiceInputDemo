package com.jay.exersiceinputdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jay.exersiceinputdemo.bean.FingerDrawRectBean;
import com.jay.exersiceinputdemo.interf.OnInputOkListener;
import com.jay.exersiceinputdemo.R;
import com.jay.exersiceinputdemo.bean.ExerciseBean;
import com.jay.exersiceinputdemo.widget.ExerciseInputView;

import java.util.List;

import static com.jay.exersiceinputdemo.activity.GetExercisePicActivity.KEY_EXERCISE_BEAN;

/**
 * desc:默认:题图根布局的背景
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String PIC_FILE_PATH = "PIC_FILE_PATH";

    private String picFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            picFilePath = intent.getExtras().getString(PIC_FILE_PATH, "");
        }
        if (TextUtils.isEmpty(picFilePath)) {
            Toast.makeText(MainActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
            finish();
        }

        ExerciseInputView inputView = findViewById(R.id.finger_slide_frame);
        inputView.setExercisePic(picFilePath);

        inputView.setOnInputOkListener(new OnInputOkListener() {
            @Override
            public void inputOkListener(List<FingerDrawRectBean> rectBeans) {
                ExerciseBean exerciseBean = new ExerciseBean(picFilePath, rectBeans);
                String toJson = new Gson().toJson(exerciseBean);
                Log.d(TAG, "toJson 字符串=" + toJson);
                Intent data = new Intent();
                data.putExtra(KEY_EXERCISE_BEAN, toJson);
                MainActivity.this.setResult(GetExercisePicActivity.REQUEST_CODE_GET_EXERCISE_BEAN, data);
            }
        });
    }
}
