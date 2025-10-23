package com.zhoufujun.assists.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zhoufujun.assists.MyApplication;
import com.zhoufujun.assists.manager.ActivityManager;
import com.zhoufujun.assists.utils.IntentFactory;

public class BaseActivity extends AppCompatActivity {
    private final static String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManage().addActivity(this);
        if (this.appHearthCheckInvalid())
            return;
        configStatusBarTextColorDark();
    }

    /**
     * 出现异常情况时候的处理
     *
     * @return true表示APP现场处于非正常状态，否则表示正常状态
     */
    private boolean appHearthCheckInvalid() {
        try {
            Log.d(TAG, "【APP异常检查】当前健康状态=" + MyApplication.getInstance().isAppHearth());
            if (!MyApplication.getInstance().isAppHearth()) {
                Log.d(TAG, "【APP异常检查】在=" + getClass().getSimpleName() + "中检查到异常，进入重启");
                MyApplication ctx = MyApplication.getInstance();
                Intent intent = IntentFactory.createMainIntent(ctx);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                return true;
            } else {
                Log.d(TAG, "【APP异常检查】当前健康状态良好" + getClass().getSimpleName());
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return false;
    }

    /**
     * 设置状态栏文字颜色为深色（系统默认是白色）。
     * <p>
     * 说明：之所以要设置为深色，是因为RainbowChat的绝大多数界面，沉浸式效果时的背景用的是白色，
     * 系统默认的状态栏文字颜色也是白色，这样的话就看不清系统的时间等等内容了，太难看。
     */
    protected void configStatusBarTextColorDark() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
