package com.zh.testbottonnav.ui;

import android.content.Context;

public class UiUtils {
    public static  int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = context.getResources().getDimensionPixelSize(resourceId);
        return height;
    }
}
