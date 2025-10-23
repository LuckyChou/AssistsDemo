package com.zhoufujun.assists.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.ven.assists.AssistsCore;
import com.zhoufujun.assists.B;
import com.zhoufujun.assists.R;
import com.zhoufujun.assists.broadcast.LockScreenReceiver;
import com.zhoufujun.assists.utils.IntentFactory;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        LockScreenReceiver lockScreenReceiver = new LockScreenReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(lockScreenReceiver, intentFilter);

        Button start = findViewById(R.id.start);

        start.setOnClickListener(v -> {
            moveTaskToBack(true);
            new Handler(Looper.getMainLooper()).postDelayed(AssistsCore.INSTANCE::home, 500);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = IntentFactory.createWxIntent(this);
                startActivity(intent);
                B.INSTANCE.gotoStep1();
            }, 1000);
        });
    }
}
