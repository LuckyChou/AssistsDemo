package com.zhoufujun.assists.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.zhoufujun.assists.activity.MainActivity;
import com.zhoufujun.assists.activity.PortalActivity;

public class IntentFactory {
    /**
     * 启动权限引导页
     *
     * @param thisActivity 上下文
     * @return 主页Intent
     */
    public static Intent createPermissionGuideIntent(Context thisActivity) {
        Intent intent = new Intent(thisActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public static Intent createPortalIntent(Context thisActivity) {
        Intent intent = new Intent(thisActivity, PortalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    /**
     * 启动主页
     *
     * @param thisActivity 上下文
     * @return 主页Intent
     */
    public static Intent createMainIntent(Context thisActivity) {
        Intent intent = new Intent(thisActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    /**
     * 启动微信
     *
     * @param thisActivity 上下文
     * @return 微信Intent
     */
    public static Intent createWxIntent(Context thisActivity) {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName component = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setComponent(component);
        return intent;
    }
}
