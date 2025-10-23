package com.zhoufujun.assists;


import androidx.multidex.MultiDexApplication;

import com.zhoufujun.assists.network.NetAPI;

public class MyApplication extends MultiDexApplication {
    public String aiAnswer = null;
    public boolean appHearth = false;
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        NetAPI.init();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public String getAiAnswer() {
        return aiAnswer;
    }

    public void setAiAnswer(String aiAnswer) {
        this.aiAnswer = aiAnswer;
        while (aiAnswer!=null && this.aiAnswer.startsWith("\n")){
            this.aiAnswer = this.aiAnswer.substring(1);
        }
    }

    public boolean isAppHearth() {
        return appHearth;
    }

    public void setAppHearth(boolean appHearthFlag) {
        this.appHearth = appHearthFlag;
    }
}
