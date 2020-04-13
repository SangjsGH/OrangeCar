package com.example.orangecar.okhttp;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by zhm on 2017/7/13.
 */

public class PostUtil {

    public static void post(Context context, final String url, final HashMap<String, String> params, OkHttpClientManager.ResultCallback callback, Object tag) {
//        params.put("system","android");
//        params.put("chanel", CommentManager.getInstance().getChanel(context));
//        params.put("token", SpUtils.getToken(context));
//        params.put("userId",SpUtils.getPhone(context));
        OkHttpClientManager.getInstance().postAsyn(context,url, params, callback,tag);
    }

    public static void get(Context context, final String url, final HashMap<String, String> params, OkHttpClientManager.ResultCallback callback) {
        OkHttpClientManager.getInstance().getAsyn(url, params, callback);
    }
}
