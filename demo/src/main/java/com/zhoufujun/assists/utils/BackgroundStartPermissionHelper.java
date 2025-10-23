package com.zhoufujun.assists.utils;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.os.Process;

public class BackgroundStartPermissionHelper {

    private static final String TAG = "BgStartPermission";

    /**
     * 检测是否允许后台弹出界面
     * 注意：各厂商实现不同，这里只是简单尝试判断
     */
    public static boolean isBackgroundStartAllowed(Context context) {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        switch (manufacturer) {
            case "xiaomi":
                return isMiuiBackgroundAllowed(context);
            case "huawei":
            case "honor":
                return isHuaweiBackgroundAllowed(context);
            case "oppo":
                return isOppoBackgroundAllowed(context);
            case "vivo":
                return isVivoBackgroundAllowed(context);
            default:
                return true; // 原生 Android 一般允许
        }
    }

    /**
     * 引导用户去后台弹出界面权限设置
     */
    public static void requestPermission(Context context) {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        Intent intent = null;
        switch (manufacturer) {
            case "xiaomi":
                intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.permissions.PermissionsEditorActivity"
                ));
                intent.putExtra("extra_pkgname", context.getPackageName());
                break;
            case "huawei":
            case "honor":
                intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.permissionmanager.ui.MainActivity"
                ));
                break;
            case "oppo":
                intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.floatwindow.FloatWindowListActivity"
                ));
                break;
            case "vivo":
                intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.FloatWindowManager"
                ));
                break;
            default:
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                break;
        }

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                // fallback
                Intent fallback = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                fallback.setData(Uri.fromParts("package", context.getPackageName(), null));
                fallback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(fallback);
            }
        }
    }

    // =================== 各厂商检测逻辑 ===================

    private static boolean isMiuiBackgroundAllowed(Context context) {
        // MIUI 没有公开 API，简单通过 AppOps 检测或直接返回 false
        AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            int op = 10021; // 小米后台弹出界面权限的 op 值
            java.lang.reflect.Method method = ops.getClass().getMethod("checkOpNoThrow",
                    new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isHuaweiBackgroundAllowed(Context context) {
        // EMUI 也没有公开 API，可以尝试通过 AppOpsManager 检测
        return true;
    }

    private static boolean isOppoBackgroundAllowed(Context context) {
        return false;
    }

    private static boolean isVivoBackgroundAllowed(Context context) {
        return false;
    }

    // =================== 使用示例 ===================
    /*
    if (!BackgroundStartPermissionHelper.isBackgroundStartAllowed(context)) {
        // 提示用户手动开启
        BackgroundStartPermissionHelper.requestPermission(context);
    } else {
        // 已经允许，可以启动 Activity
    }
    */
}

