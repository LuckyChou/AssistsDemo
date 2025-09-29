package com.zhoufujun.assists.network.entity;

import java.util.List;

public class Response {
    private List<Choice> choices;

    public List<Choice> getChoices() { return choices; }

    @Override
    public String toString() {
        return "AiResponse{" +
                "choices=" + choices +
                '}';
    }

    public static class Choice {
        private Message message;

        public Message getMessage() { return message; }

        @Override
        public String toString() {
            return "Choice{" +
                    "message=" + message +
                    '}';
        }
    }

    public static class Message {
        private String role;
        private String content;

        public String getRole() { return role; }
        public String getContent() { return content; }

        @Override
        public String toString() {
            return "Message{" +
                    "role='" + role + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
