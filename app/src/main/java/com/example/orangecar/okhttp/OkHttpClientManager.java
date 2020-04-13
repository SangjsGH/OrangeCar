package com.example.orangecar.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.orangecar.until.JsonUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpClientManager {

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson gson ;
    private GetDelegate mGetDelegate = new GetDelegate();

    /**
     * 私有构造方法
     */
    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    /**
     * 单例设计模式
     * @return
     */
    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * hashmap 参数传入方式
     * @param url
     * @param params
     * @param callback
     */
    public  void postAsyn(Context context, String url, Map<String, String> params, ResultCallback callback, Object tag) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostFormRequest(url, paramsArr, tag);
        deliveryResult(context,callback, request);
    }




    public static void getAsyn(String url, HashMap<String, String> params, ResultCallback callback) {
        if (!params.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            int num = 0;
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                if (num == 0) {
                    url = url + "?" + key + "=" + entry.getValue();
                } else {
                    url = url + "&" + key + "=" + entry.getValue();
                }
                num++;
            }
        }
        getInstance().getGetDelegate().getAsyn(url, callback, null);
    }


    public GetDelegate getGetDelegate() {
        return mGetDelegate;
    }

    /**
     * 回调接口
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }



    /**
     * 获取Request
     * @param url
     * @param params
     * @param tag
     * @return
     */
    private Request buildPostFormRequest(String url, Param[] params, Object tag) {
        if (params == null) {
            params = new Param[0];
        }

        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            if(!TextUtils.isEmpty(param.key) && !TextUtils.isEmpty(param.value)){
                builder.add(param.key,param.value);
            }
        }

        RequestBody requestBody = builder.build();
        Request request=new Request.Builder().addHeader("Accept-Encoding", "gzip").post(requestBody).tag(tag).url(url).build();
        return request;
    }

    /**
     * 定义一个对象，用于hashmap转换成对象
     */
    public  class Param {
        String key;
        String value;
        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * hashmap转换成对象
     */
    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }




    /**
     * 最后的回调
     * @param callback
     * @param request
     */
    private void deliveryResult(final Context context , final ResultCallback callback, final Request request) {
         Call call = mOkHttpClient.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailedStringCallback(request, e, callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    boolean successful = response.isSuccessful();
                    if (successful) {
                        InputStream inputStream = response.body().byteStream();
                        GZIPInputStream gzipIn = new GZIPInputStream(inputStream);
                        // inputStream-->string
                    } else {
                        sendFailedStringCallback(request, new Exception("未知异常"), callback);
                    }
                }
            });
    }



    public static String convertStreamToString(InputStream is) throws IOException {
        try {
            if (is != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                    // BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    while ((line = reader.readLine()) != null) {
                        // sb.append(line);
                        sb.append(line).append("\n");
                    }
                } finally {
                    is.close();
                }
                return sb.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 失败
     * @param request
     * @param e
     * @param callback
     */
    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(request, e);
            }
        });
    }

    /**
     * 成功
     * @param object
     * @param callback
     */
    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
            }
        });
    }

    /**
     * 取消网络请求
     * @param tag
     */
    public  void cancelTag(Object tag) {
        cancelCallsWithTag(tag);
    }

    /**
     * 取消网络请求
     * @param tag
     */
    public void cancelCallsWithTag(Object tag) {
        if (tag == null) {
            return;
        }
        synchronized (mOkHttpClient.dispatcher().getClass()) {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }

            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }
        }
    }



    /**
     * 最后的回调
     *
     * @param callback
     * @param request
     */
    private void deliveryResult(final ResultCallback callback, final Request request) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean successful = response.isSuccessful();
                if (successful) {
                    String string = response.body().string();
                    int status = JsonUtils.getStatus(string);
                    sendSuccessResultCallback(string, callback);
                } else {
                    sendFailedStringCallback(request, new Exception("未知异常"), callback);
                }
            }
        });
    }



    //====================GetDelegate=======================
    public class GetDelegate {

        private Request buildGetRequest(String url, Object tag) {
            Request.Builder builder = new Request.Builder()
                    .url(url);

            if (tag != null) {
                builder.tag(tag);
            }

            return builder.build();
        }

        /**
         * 通用的方法
         */
        public Response get(Request request) throws IOException {
            Call call = mOkHttpClient.newCall(request);
            Response execute = call.execute();
            return execute;
        }

        /**
         * 同步的Get请求
         */
        public Response get(String url) throws IOException {
            return get(url, null);
        }

        public Response get(String url, Object tag) throws IOException {
            final Request request = buildGetRequest(url, tag);
            return get(request);
        }


        /**
         * 同步的Get请求
         */
        public String getAsString(String url) throws IOException {
            return getAsString(url, null);
        }

        public String getAsString(String url, Object tag) throws IOException {
            Response execute = get(url, tag);
            return execute.body().string();
        }

        /**
         * 通用的方法
         */
        public void getAsyn(Request request, ResultCallback callback) {
            deliveryResult(callback, request);
        }

        /**
         * 异步的get请求
         */
        public void getAsyn(String url, final ResultCallback callback) {
            getAsyn(url, callback, null);
        }

        public void getAsyn(String url, final ResultCallback callback, Object tag) {
            final Request request = buildGetRequest(url, tag);
            getAsyn(request, callback);
        }
    }
}
