package com.zhoufujun.assists.network.entity;

import java.util.List;

public class Model {
//    private final String model = "Qwen/Qwen2.5-7B-Instruct";
    private final String model = "Qwen/Qwen3-8B";
    private List<Message> messages;

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}