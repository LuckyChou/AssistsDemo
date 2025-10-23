package com.zhoufujun.assists.utils;

import android.app.Activity;
import android.widget.Toast;

import com.ven.assists.AssistsCore;

public class AccessibilityUtil {
    public static boolean checkAccessibility(Activity activity){
        if (AssistsCore.INSTANCE.isAccessibilityServiceEnabled()) {
            Toast.makeText(activity, "服务已经开启", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            AssistsCore.INSTANCE.openAccessibilitySetting();
            return false;
        }
    }
}
