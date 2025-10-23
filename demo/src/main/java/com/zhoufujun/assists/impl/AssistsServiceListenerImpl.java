package com.zhoufujun.assists.impl;


import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.ven.assists.AssistsCore;
import com.ven.assists.service.AssistsService;
import com.ven.assists.service.AssistsServiceListener;
import com.zhoufujun.assists.utils.IntentFactory;

public class AssistsServiceListenerImpl implements AssistsServiceListener {
    @Override
    public void onAccessibilityEvent(@NonNull AccessibilityEvent event) {

    }

    @Override
    public void onServiceConnected(@NonNull AssistsService service) {
        if (!AssistsCore.INSTANCE.getPackageName().equals(AppUtils.getAppPackageName())) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = IntentFactory.createMainIntent(service);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }, 800);
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onUnbind() {

    }

    @Override
    public void screenCaptureEnable() {

    }
}
