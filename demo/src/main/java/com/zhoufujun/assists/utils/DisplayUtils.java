package com.zhoufujun.assists.utils;

import android.content.Context;

public class DisplayUtils {

    /**
     * dp 转 px
     */
    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * px 转 dp
     */
    public static int pxToDp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(px / density);
    }

    /**
     * sp 转 px（用于字体大小）
     */
    public static int spToPx(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(sp * scaledDensity);
    }

    /**
     * px 转 sp（用于字体大小）
     */
    public static int pxToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(px / scaledDensity);
    }
}
