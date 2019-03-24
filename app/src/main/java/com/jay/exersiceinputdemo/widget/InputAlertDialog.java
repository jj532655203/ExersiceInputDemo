package com.jay.exersiceinputdemo.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jay.exersiceinputdemo.interf.OnAnswerOkListener;
import com.jay.exersiceinputdemo.R;
import com.jay.exersiceinputdemo.utils.DisplayUtil;

/**
 * Created by jj on 2019/3/24.
 */

public class InputAlertDialog extends DialogFragment {

    private OnAnswerOkListener mListener;
    private EditText mContent;
    private Button mPositiveBtn, mNegativeBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.input_alert_dialog, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        int windowWidth = DisplayUtil.getWindowWidth(getContext());
        int windowHeight = DisplayUtil.getWindowHeight(getContext());

        int width = Math.max(windowWidth, windowHeight);
        int height = Math.min(windowWidth, windowHeight);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout((int) (width * 0.53), (int) (height * 0.56));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //        initData();
        initEvent();
    }

    private void initEvent() {
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onAnswerOk(mContent.getText().toString());
                dismiss();
            }
        });
        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView(View view) {
        mContent = view.findViewById(R.id.edt);
        mPositiveBtn = view.findViewById(R.id.positive_btn);
        mNegativeBtn = view.findViewById(R.id.negative_btn);
    }

    public void setOnInputResultListener(OnAnswerOkListener listener) {
        mListener = listener;
    }
}
