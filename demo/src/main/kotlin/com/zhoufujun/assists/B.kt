package com.zhoufujun.assists

import com.ven.assists.stepper.StepManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object B {
    fun gotoStep1() {
        GlobalScope.launch {
            StepManager.execute(W::class.java, 1, begin = true)
        }
    }
}