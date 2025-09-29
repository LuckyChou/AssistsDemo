package com.zhoufujun.assists;

import android.app.Application;

import com.zhoufujun.assists.network.NetAPI;

public class MyApplication extends Application {
    public String aiAnswer = null;
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
}
