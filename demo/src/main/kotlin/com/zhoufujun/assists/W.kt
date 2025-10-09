package com.zhoufujun.assists

import android.os.Bundle
import android.view.accessibility.AccessibilityNodeInfo
import com.ven.assists.AssistsCore
import com.ven.assists.AssistsCore.click
import com.ven.assists.AssistsCore.findById
import com.ven.assists.AssistsCore.getBoundsInScreen
import com.ven.assists.stepper.Step
import com.ven.assists.stepper.StepCollector
import com.ven.assists.stepper.StepImpl
import com.zhoufujun.assists.network.AiUtil
import com.zhoufujun.assists.utils.DisplayUtils
import kotlinx.coroutines.delay


class W : StepImpl() {
    override fun onImpl(collector: StepCollector) {
        collector.next(1) {
            runMain { OverlayBool.show() }
            return@next Step.none
        }.next(2) {
            LogWrapper.logAppend("开始获取数据")
            var findById = AssistsCore.findById("com.tencent.mm:id/n9a")
            run outside@{
                findById.forEach {
                    run inner@{
                        val cut = it.findById("com.tencent.mm:id/cut")
                        if(cut.isNotEmpty()) {
                            cut.forEach { it ->
                                val boundsInScreen = it.getBoundsInScreen()
                                if (boundsInScreen.bottom < 0) {
                                    LogWrapper.logAppend("超出屏幕范围，跳过")
                                    return@inner
                                } else {
                                    LogWrapper.logAppend("获取到数据：${it.text}")
                                    LogWrapper.logAppend("开始生成回复")
                                    MyApplication.getInstance().setAiAnswer(AiUtil.getAiAnswer(it.text.toString()))
                                    LogWrapper.logAppend("回复")
                                    LogWrapper.logAppend("准备回复（按确定执行回复） : " + MyApplication.getInstance().getAiAnswer())
                                    return@outside
                                }
                            }
                        }else{
                            val cuu = it.findById("com.tencent.mm:id/cuu")
                            if(cuu.isNotEmpty()){
                                delay(500)
                                runMain { OverlayBool.hide() }
                                delay(1000)
                                AssistsCore.gestureClick(
                                    cuu[0].getBoundsInScreen().centerX().toFloat(),
                                    cuu[0].getBoundsInScreen().centerY().toFloat(),
                                    20
                                )
                                delay(500)
                                runMain { OverlayBool.show() }
                                delay(1000)
                                val nbx = AssistsCore.findById("com.tencent.mm:id/nbx")
                                nbx.forEach {
                                    LogWrapper.logAppend("获取到数据：${it.text}")
                                    LogWrapper.logAppend("开始生成回复")
                                    MyApplication.getInstance().setAiAnswer(AiUtil.getAiAnswer(it.text.toString()))
                                    LogWrapper.logAppend("回复")
                                    LogWrapper.logAppend("准备回复（按确定执行回复） : " + MyApplication.getInstance().getAiAnswer())
                                    AssistsCore.back()
                                }
                            }
                        }
                    }
                }
            }
            return@next Step.none
        }.next(3) {
            try {
                if (MyApplication.getInstance().getAiAnswer() != null) {
                    LogWrapper.logAppend("开始执行回复")
                    run outside@{
                        findById("com.tencent.mm:id/n9a").forEach { outSideIt ->
                            run inner@{
                                var cut = outSideIt.findById("com.tencent.mm:id/cut")
                                if(cut.isEmpty()){
                                    cut = outSideIt.findById("com.tencent.mm:id/cuu")
                                }
                                cut.forEach { it ->
                                    val boundsInScreen = it.getBoundsInScreen()
                                    if (boundsInScreen.bottom < 0) {
                                        return@inner
                                    } else {
                                        outSideIt.findById("com.tencent.mm:id/r2").forEach { it ->
                                            delay(1000)
                                            runMain { OverlayBool.hide() }
                                            delay(500)
                                            it.click()
                                            val boundsInScreen = it.getBoundsInScreen()
                                            val centerX = DisplayUtils.pxToDp(
                                                MyApplication.getInstance(),
                                                boundsInScreen.centerX().toFloat()
                                            ).toFloat()
                                            val centerY = DisplayUtils.pxToDp(
                                                MyApplication.getInstance(),
                                                boundsInScreen.centerY().toFloat()
                                            ).toFloat()//897   1779   [686,1725][945,1833]
                                            //                            LogWrapper.logAppend("点击【评论】按钮，坐标：$boundsInScreen")
                                            delay(1000)
                                            AssistsCore.gestureClick(
                                                DisplayUtils.dpToPx(MyApplication.getInstance(), centerX - 47).toFloat(),
                                                DisplayUtils.dpToPx(MyApplication.getInstance(), centerY).toFloat(),
                                                20
                                            )
                                            val editView = AssistsCore.findById("com.tencent.mm:id/p0", null, null, "android.widget.EditText")
                                            if (editView.isEmpty()) {
                                                LogWrapper.logAppend("没找到文本框，退出")
                                                runMain { OverlayBool.show() }
                                                return@next Step.none
                                            }
                                            val edit = editView[0]
                                            delay(1000)
                                            val arguments = Bundle()
                                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, MyApplication.getInstance().getAiAnswer())
                                            val paste = edit.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
//                                            }
                                            LogWrapper.logAppend("评论写入状态：${paste}")
                                            delay(3000)
                                            val sendBtn = AssistsCore.findById("com.tencent.mm:id/p4", null, null, "android.widget.Button")
                                            sendBtn[0].click()
                                            runMain { OverlayBool.show() }
                                            LogWrapper.logAppend("完成")
                                            return@outside
                                        }
                                    }
                                }
                            }
                        }
                    }
                    MyApplication.getInstance().setAiAnswer(null);
                } else {
                    LogWrapper.logAppend("还没有生成回复")
                }
            } catch (e: Exception) {
                MyApplication.getInstance().setAiAnswer(null);
                LogWrapper.logAppend("发生错误，结束此轮")
                runMain {
                    if (!OverlayBool.showed) {
                        OverlayBool.show()
                    }
                }
            }
            return@next Step.none
        }
    }
}