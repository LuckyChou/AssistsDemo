package com.zhoufujun.assists.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.ven.assists.service.AssistsService;
import com.zhoufujun.assists.MyApplication;
import com.zhoufujun.assists.R;
import com.zhoufujun.assists.impl.AssistsServiceListenerImpl;
import com.zhoufujun.assists.utils.AccessibilityUtil;
import com.zhoufujun.assists.utils.BackgroundStartPermissionHelper;
import com.zhoufujun.assists.utils.IntentFactory;

public class PermissionGuideActivity extends BaseActivity {
    private Button backgroundPopup,accessible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
            }
        }
        MyApplication.getInstance().setAppHearth(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final View view = View.inflate(this, R.layout.guide_activity, null);
        setContentView(view);

        AssistsService.Companion.getListeners().add(new AssistsServiceListenerImpl());

        backgroundPopup = findViewById(R.id.enable_background_popup);
        accessible = findViewById(R.id.enable_accessible);

        backgroundPopup.setOnClickListener(v -> {
            if (!BackgroundStartPermissionHelper.isBackgroundStartAllowed(this)) {
                BackgroundStartPermissionHelper.requestPermission(this);
            } else {
                backgroundPopup.setText("已经开启后台弹窗");
                backgroundPopup.setClickable(false);
                backgroundPopup.setEnabled(false);
            }
        });

        accessible.setOnClickListener(v -> {
            if (AccessibilityUtil.checkAccessibility(this)) {
                startActivity(IntentFactory.createMainIntent(this));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        accessible.callOnClick();
    }
}
