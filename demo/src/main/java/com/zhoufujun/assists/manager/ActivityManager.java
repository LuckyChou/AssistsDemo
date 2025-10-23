package com.zhoufujun.assists.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Stack;

/**
 * Activity管理类
 *
 * @author 周福钧
 * @version 1.0
 */
public class ActivityManager {
    private final static String TAG = ActivityManager.class.getSimpleName();
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 获取实例
     *
     * @return 当前实例
     */
    public static ActivityManager getActivityManage() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        Log.d(TAG, "【Frame】ActivityManager" + activity.getClass().getCanonicalName() + "(hash=" + activity.hashCode() + ")正在被压入堆栈。");
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    public static Activity currentActivity() {
        if (activityStack != null) {
            return activityStack.lastElement();
        }
        return null;
    }

    /**
     * 移除当前Activity
     */
    public void removeActivity() {
        removeActivity(activityStack.lastElement());
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            Log.d(TAG, "【Frame】ActivityManager" + activity.getClass().getCanonicalName() + "(hash=" + activity.hashCode() + ")正在被弹出堆栈。");
            activityStack.remove(activity);
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity.
     * <p>
     * 正常情况下，Activity是会自己finish的，但是程序往往不正常，你知道我在说什么。
     */
    public void finishAllActivity() {
        if (activityStack == null)
            return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (null != activity) {
                Log.d(TAG, "【Frame】ActivityManager" + activity.getClass().getCanonicalName() + "(hash=" + activity.hashCode() + ")正在被弹出堆栈。");
                try {
                    activity.finish();
                } catch (Exception e) {
                    Log.w(TAG, "【Frame】ActivityManager" + activity.getClass().getCanonicalName() + "(hash=" + activity.hashCode() + ")在finish时出错。");
                }
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitAPP(Context context) {
        try {
            finishAllActivity();
            Log.w(TAG, "【Frame】ActivityManager正在退出APP");
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startMain);
            System.exit(0);
        } catch (Exception e) {
            Log.w(TAG, e.getMessage(), e);
        }
    }
}