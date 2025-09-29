package com.zhoufujun.assists.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetAPI {
    final static String TAG = "NetAPI";
    final static boolean isDebug = true;
    private static NetAPI netAPI = null;
    private static Retrofit mRetrofit;

    long writeTimeOut = 1000;
    long readTimeOut = 1000;
    long connectTimeOut = 200;

    public static void init() {
        if (mRetrofit != null)
            throw new RuntimeException("NetApi has been initialized");
        getInstance();
    }
//
//	public static void unInit() {
//		mRetrofit = null;
//		netAPI = null;
//		disposableHashMap.clear();
//		disposableHashMap = null;
//	}

    private NetAPI() {
        OkHttpClient.Builder build = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                .cookieJar(cookieJar())
                .addInterceptor(tokenInterceptor());

        if (isDebug) {
            build.addInterceptor(logInterceptor());//添加日志拦截器
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL + "v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(build.build())
                .build();
    }


    public static <T> T API(Class<T> service) {
        initException();
        return mRetrofit.create(service);
    }

    private static void initException() {
        if (null == mRetrofit)
            throw new NullPointerException("NetAPI is not initialized");
    }

    private static synchronized NetAPI getInstance() {
        if (null == netAPI) {
            netAPI = new NetAPI();
        }
        return netAPI;
    }

    private HttpLoggingInterceptor logInterceptor() {
        return new HttpLoggingInterceptor(message -> Log.w(TAG, message)).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }

    /**
     * 设置请求头token
     *
     * @return
     */
    private Interceptor tokenInterceptor() {
        return new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + Constant.API_KEY)
                        .method(originalRequest.method(), originalRequest.body())
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }

    /**
     * 动态自动设置请求头cookie
     *
     * @return
     */
    private CookieJar cookieJar() {
        return new CookieJar() {
            private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> cookieList) {
                cookieStore.put(buildKey(httpUrl), cookieList);
            }

            @NotNull
            @Override
            public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                List<Cookie> cookies = cookieStore.get(buildKey(httpUrl));
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }

            private String buildKey(@NotNull HttpUrl httpUrl) {
                return httpUrl.host() + "_" + httpUrl.port();
            }
        };
    }
}

