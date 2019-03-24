package com.jay.exersiceinputdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jj on 2019/3/24.
 */

public class ExerciseBean implements Serializable {

    private static final long serialVersionUID = -4740275839364511049L;

    //    public static final int SHARE

    private String picFilePath;
    private List<FingerDrawRectBean> mDrawRectBeans;

    public ExerciseBean(String picFilePath, List<FingerDrawRectBean> drawRectBeans) {
        this.picFilePath = picFilePath;
        mDrawRectBeans = drawRectBeans;
    }

    @Override
    public String toString() {
        return "ExerciseBean{" +
                "picFilePath='" + picFilePath + '\'' +
                ", mDrawRectBeans=" + mDrawRectBeans +
                '}';
    }

    public String getPicFilePath() {
        return picFilePath;
    }

    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }

    public List<FingerDrawRectBean> getDrawRectBeans() {
        return mDrawRectBeans;
    }

    public void setDrawRectBeans(List<FingerDrawRectBean> drawRectBeans) {
        mDrawRectBeans = drawRectBeans;
    }
}
