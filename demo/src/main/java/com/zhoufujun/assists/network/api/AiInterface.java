package com.zhoufujun.assists.network.api;

import com.zhoufujun.assists.network.entity.Model;
import com.zhoufujun.assists.network.entity.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AiInterface {
    @POST("chat/completions")
    Call<Response> getAnswer(@Body Model aiModel);

}
