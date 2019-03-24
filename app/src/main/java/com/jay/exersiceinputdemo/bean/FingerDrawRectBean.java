package com.jay.exersiceinputdemo.bean;

import android.graphics.Rect;

import java.io.Serializable;

/**
 * Created by jj on 2019/3/24.
 */

public class FingerDrawRectBean implements Serializable {

    private static final long serialVersionUID = 7756637317680770142L;
    private Rect rect;
    private String answer;

    public FingerDrawRectBean(Rect rect, String answer) {
        this.rect = rect;
        this.answer = answer;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "FingerDrawRectBean{" +
                "rect=" + rect +
                ", answer='" + answer + '\'' +
                '}';
    }
}
