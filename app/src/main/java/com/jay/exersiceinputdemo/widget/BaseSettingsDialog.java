package com.jay.exersiceinputdemo.widget;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jay.exersiceinputdemo.R;
import com.jay.exersiceinputdemo.utils.DisplayUtil;

import java.util.List;

/**
 * Created by HP on 2017/11/28.
 */

public abstract class BaseSettingsDialog extends DialogFragment {

    private ConstraintLayout mLlContainer;
    //内容区
    private FrameLayout mFlContainer;
    //返回按钮的文字
    private TextView mTvLeftHint;
    private TextView mTvNext;
    private ImageView mIvLeftArrow;
    private boolean mIsPushAnim;
    private String mDismissDialogTag;
    public static final String ANIMATION_MODE_KEY = "isPushAnim";
    public static final String DIALOG_DELAY_DISMIIS_KEY = "delayDismissTag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle();
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        mIsPushAnim = bundle.getBoolean(ANIMATION_MODE_KEY);
        mDismissDialogTag = bundle.getString(DIALOG_DELAY_DISMIIS_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_setting_dialog, container, false);
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            if (mIsPushAnim) {
                window.setWindowAnimations(R.style.settingsStylePush);
            } else {
                window.setWindowAnimations(R.style.settingsStyleFade);
            }
        }
        return dialog;
    }*/

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLlContainer = (ConstraintLayout) view.findViewById(R.id.ll_container);
        TextView mTvSettingTitle = (TextView) view.findViewById(R.id.tv_setting_title);
        mFlContainer = (FrameLayout) view.findViewById(R.id.fl_container);
        mTvLeftHint = (TextView) view.findViewById(R.id.tv_left_hint);
        mTvNext = (TextView) view.findViewById(R.id.tv_next);
        mIvLeftArrow = (ImageView) view.findViewById(R.id.iv_left_arrow);

        if (getContentLayout() != -1)
            mFlContainer.addView(LayoutInflater.from(getContext()).inflate(getContentLayout(), mFlContainer, false));

        if (!TextUtils.isEmpty(titleString())) {
            mTvSettingTitle.setText(titleString());
        }

        initView();
        initData();
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        //新dialog获取焦点后,旧dialog再diamiss
        if (!TextUtils.isEmpty(mDismissDialogTag)) {
            List<Fragment> fragments = getFragmentManager().getFragments();
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (fragment == null) continue;
                if (fragment.getTag().equalsIgnoreCase(mDismissDialogTag))
                    ((DialogFragment) fragment).dismiss();
            }
        }
    }

    protected abstract
    @LayoutRes
    int getContentLayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected FrameLayout getContentView() {
        return mFlContainer;
    }

    protected abstract String titleString();

    protected void setBackBtnVisible(boolean flag) {
        mIvLeftArrow.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
        if (!flag) setOnBackClickListener(null);
    }

    /**
     * 默认:可见
     *
     * @param flag
     */
    protected void setBackBtnTextVisible(boolean flag) {
        mTvLeftHint.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 默认: 返回
     *
     * @param str
     */
    protected void setBackBtnText(String str) {
        if (str == null) return;
        mTvLeftHint.setVisibility(View.VISIBLE);
        mTvLeftHint.setText(str);
    }

    /**
     * 默认:可见
     *
     * @param flag default false
     */
    protected void setNextBtnVisible(boolean flag) {
        mTvNext.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
        if (!flag) setOnNextBtnClickListener(null);
    }

    /**
     * 默认:下一步
     *
     * @param str
     */
    protected void setNextBtnText(String str) {
        if (str == null) return;
        mTvNext.setVisibility(View.VISIBLE);
        mTvNext.setText(str);
    }

    protected void setTitleText(String str) {
        if (str == null) return;
        TextView title = (TextView) mLlContainer.findViewById(R.id.tv_setting_title);
        title.setText(str);
    }

    /**
     * 设置下方内容区的背景
     *
     * @param id drawable Id
     */
    protected void setContentViewBg(int id) {
        mFlContainer.setBackground(ContextCompat.getDrawable(getContext(), id));
    }

    protected void setOnBackClickListener(final OnBackClickListener lisener) {
        mLlContainer.findViewById(R.id.ll_left_hint_container).setOnClickListener(lisener == null ? null : new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lisener.onBackBtnClicked();
            }
        });
    }

    public interface OnBackClickListener {
        void onBackBtnClicked();
    }

    protected void setOnNextBtnClickListener(final OnNextBtnClickListener lisener) {
        mLlContainer.findViewById(R.id.tv_next).setOnClickListener(lisener == null ? null : new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lisener.onNextBtnClicked();
            }
        });
    }

    interface OnNextBtnClickListener {
        void onNextBtnClicked();
    }


}
