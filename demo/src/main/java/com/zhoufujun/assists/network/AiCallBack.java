package com.zhoufujun.assists.network;

public interface AiCallBack {
    void onSuccess(String response);

    void onFailure(Throwable t);
}
