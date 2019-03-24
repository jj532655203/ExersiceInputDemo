package com.jay.exersiceinputdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jay.exersiceinputdemo.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by jj on 2019/3/24.
 * desc:获取题图(从文件浏览器导入)
 */

public class ObtainExercisePicActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GET_CONTENT = 0;
    public static final int REQUEST_CODE_GET_EXERCISE_BEAN = 1;
    public static final String KEY_EXERCISE_BEAN = "KEY_EXERCISE_BEAN";
    private static final String TAG = ObtainExercisePicActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtain_exercise_pic);

    }

    public void importExercisePicFromFileBrowser(View view) {
        RxPermissions permissions = new RxPermissions(this);
        permissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {

                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(intent, REQUEST_CODE_GET_CONTENT);
                        } else {
                            Toast.makeText(ObtainExercisePicActivity.this, "去写权限被拒绝,功能不可用", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_GET_CONTENT:
                Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                if (uri == null)
                    return;

                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = getContentResolver().query(uri, proj, null, null, null);
                if (actualimagecursor == null)
                    return;

                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor.getString(actual_image_column_index);
                actualimagecursor.close();
                Toast.makeText(this, img_path, Toast.LENGTH_SHORT).show();

                //打开Mainactivity录制输入框
                Intent intent = new Intent(ObtainExercisePicActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.PIC_FILE_PATH, img_path);
                startActivityForResult(intent, REQUEST_CODE_GET_EXERCISE_BEAN);
                break;

            case REQUEST_CODE_GET_EXERCISE_BEAN:
                //保存一个题对象到本地
                Bundle extras = data.getExtras();
                if (extras != null && !TextUtils.isEmpty(extras.getString(KEY_EXERCISE_BEAN))) {
                    String string = extras.getString(KEY_EXERCISE_BEAN);
                    Log.d(TAG, "保存到本地的json对象(题对象)" + string);
                }
                break;

        }
    }
}
