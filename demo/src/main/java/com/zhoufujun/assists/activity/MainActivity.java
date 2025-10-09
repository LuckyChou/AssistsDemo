package com.zhoufujun.assists.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.ven.assists.AssistsCore;
import com.ven.assists.service.AssistsService;
import com.ven.assists.service.AssistsServiceListener;
import com.zhoufujun.assists.B;
import com.zhoufujun.assists.R;
import com.zhoufujun.assists.broadcast.LockScreenReceiver;

public class MainActivity extends AppCompatActivity implements AssistsServiceListener {

    private boolean isActivityResumed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        AssistsService.Companion.getListeners().add(this);
        LockScreenReceiver lockScreenReceiver = new LockScreenReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(lockScreenReceiver, intentFilter);
        checkServiceEnable();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onAccessibilityEvent(@NonNull AccessibilityEvent event) {
//        AssistsServiceListener.super.onAccessibilityEvent(event);
    }

    @Override
    public void onServiceConnected(@NonNull AssistsService service) {
        if (!AssistsCore.INSTANCE.getPackageName().equals(AppUtils.getAppPackageName())) {
//            startActivity(getPackageManager().getLaunchIntentForPackage(AppUtils.getAppPackageName()));
            checkServiceEnable();
        }
    }

    @Override
    public void onInterrupt() {
//        AssistsServiceListener.super.onInterrupt();
    }

    @Override
    public void onUnbind() {
//        AssistsServiceListener.super.onUnbind();
    }

    @Override
    public void screenCaptureEnable() {
//        AssistsServiceListener.super.screenCaptureEnable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkServiceEnable();
    }

    private void checkServiceEnable() {
        if (AssistsCore.INSTANCE.isAccessibilityServiceEnabled()) {
            Toast.makeText(this, "服务已经开启，在悬浮窗中使用", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName component = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setComponent(component);
            startActivity(intent);
            B.INSTANCE.gotoStep1();
        } else {
            AssistsCore.INSTANCE.openAccessibilitySetting();
        }
    }
}
