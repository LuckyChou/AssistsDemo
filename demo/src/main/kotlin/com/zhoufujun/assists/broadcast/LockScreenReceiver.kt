package com.zhoufujun.assists.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ven.assists.window.WindowMinimizeManager
import com.zhoufujun.assists.OverlayBool

class LockScreenReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SCREEN_OFF -> {
                if (OverlayBool.showed) {
                    OverlayBool.hide()
                }
                WindowMinimizeManager.hide()
            }

            Intent.ACTION_SCREEN_ON -> {
            }

            Intent.ACTION_USER_PRESENT -> {
                WindowMinimizeManager.show()
            }
        }
    }
}