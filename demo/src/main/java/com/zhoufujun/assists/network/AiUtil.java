package com.zhoufujun.assists.network;

import com.zhoufujun.assists.network.api.AiInterface;
import com.zhoufujun.assists.network.entity.Message;
import com.zhoufujun.assists.network.entity.Model;
import com.zhoufujun.assists.network.entity.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class AiUtil {
    public static void getAiAnswer(String question, AiCallBack aiCallBack) {
        NetAPI.API(AiInterface.class).getAnswer(createAiModel(question)).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response body = response.body();
                if (body != null) {
                    aiCallBack.onSuccess(body.getChoices().get(0).getMessage().getContent());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                aiCallBack.onFailure(t);
            }
        });
    }

    public static String getAiAnswer(String question) throws IOException {
        return Objects.requireNonNull(NetAPI.API(AiInterface.class).getAnswer(createAiModel(question)).execute().body()).getChoices().get(0).getMessage().getContent();
    }

    private static Model createAiModel(String question) {
        ArrayList<Message> messages = new ArrayList<>();
        Message system = new Message("system", "你是一位温暖、体贴、善解人意的朋友，善于用简短的话语给予他人情绪价值。\n" +
                "当用户提供一段朋友圈文案时，你需要根据这段文案的内容和情绪，生成一条简短的、有温度的回复。\n" +
                "回复要求：\n" +
                "1. 语气自然，像朋友之间的互动，不要生硬或机械。\n" +
                "2. 根据朋友圈文案的情绪来调整回复风格：\n" +
                "   - 开心/喜悦 → 表达祝贺、分享快乐\n" +
                "   - 难过/低落 → 给予安慰和理解\n" +
                "   - 疲惫/压力大 → 表达关心与支持\n" +
                "   - 励志/积极 → 表达赞赏和鼓励\n" +
                "   - 怀旧/感慨 → 表达共鸣与理解\n" +
                "   - 抱怨/吐槽 → 表达认同和陪伴\n" +
                "3. 回复简短有力，建议10~30字。\n" +
                "4. 避免长篇说教，也不要显得过于官方\n" +
                "5. 可以适当使用emoji表情符号\n" +
                "6. 只返回评论内容，不要多余解释、引号、和不必要的换行。");
        messages.add(system);
        Message user = new Message("user", question);
        messages.add(user);
        Model aiModel = new Model();
        aiModel.setMessages(messages);
        return aiModel;
    }
}
