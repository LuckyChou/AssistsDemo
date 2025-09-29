package com.zhoufujun.assists

import com.ven.assists.AssistsCore
import com.ven.assists.AssistsCore.findById
import com.ven.assists.AssistsCore.getBoundsInScreen
import com.ven.assists.stepper.Step
import com.ven.assists.stepper.StepCollector
import com.ven.assists.stepper.StepImpl

class W : StepImpl() {
    override fun onImpl(collector: StepCollector) {
        collector.next(1) {
            runMain { OverlayBool.show() }
            return@next Step.none
        }.next(2) {
            var findById = AssistsCore.findById("com.tencent.mm:id/n9a")
            run outside@{
                findById.forEach {
                    run inner@{
                        val wenAnView = it.findById("com.tencent.mm:id/cut").forEach {
                            val boundsInScreen = it.getBoundsInScreen()
                            if (boundsInScreen.bottom < 0) {
                                return@inner
                            } else {
                                LogWrapper.logAppend(boundsInScreen.toString())
                                LogWrapper.logAppend(it.text)
                                return@outside
                            }

                        }
                    }
                }
            }
            return@next Step.none
        }
    }
}