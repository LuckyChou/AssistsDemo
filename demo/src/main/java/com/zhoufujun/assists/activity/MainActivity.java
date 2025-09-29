package com.zhoufujun.assists.activity;

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

public class MainActivity extends AppCompatActivity implements AssistsServiceListener {

    private boolean isActivityResumed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        AssistsService.Companion.getListeners().add(this);
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

    private void checkServiceEnable() {
        if (AssistsCore.INSTANCE.isAccessibilityServiceEnabled()) {
            Toast.makeText(this, "服务已经开启，在悬浮窗中使用", Toast.LENGTH_SHORT).show();
            AssistsCore.INSTANCE.home();
            B.INSTANCE.gotoStep1();
//            startActivity(getPackageManager().getLaunchIntentForPackage(AppUtils.getAppPackageName()));
        } else {
            AssistsCore.INSTANCE.openAccessibilitySetting();
        }
    }
}
