package com.jay.exersiceinputdemo.utils;

import android.content.Context;

/**
 * Created by jj on 2019/3/24.
 */

public class DisplayUtil {
    private static int width, height;

    public static int getWindowWidth(Context context) {
        if (width > 0)
            return width;
        if (context == null)
            return -1;
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getWindowHeight(Context context) {
        if (height > 0)
            return height;
        if (context == null)
            return -1;
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
