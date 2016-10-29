package com.xiaoyalabs.rushnet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongjun on 2016/10/27.
 * http请求类
 */

public abstract class Request<T> {
    //请求的url
    protected String mUrl = "";

    //请求方法
    protected HttpMethod mHttpMethod = HttpMethod.GET;

    //请求优先级
    protected Priority mPriority = Priority.NORMAL;

    //请求结果监听器
    protected RequestListener<T> mListener;

    //请求头
    protected Map<String,String> mHeaders = new HashMap<String,String>();

    //请求体
    protected Map<String,String> mBody = new HashMap<String,String>();

    //是否取消请求
    protected boolean isCancel = false;

    //是否缓存
    protected boolean isCache = false;

    //默认编码
    public static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    //Content-type
    public final static String HEADER_CONTENT_TYPE = "Content-Type";

    public Request(String url,HttpMethod method,RequestListener<T> listener){
        mUrl = url != null ? url : "";
        mHttpMethod = method;
        mListener = listener;
    }

    public String getUrl() {
        return mUrl;
    }

    public Request<T> setUrl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    public Request<T> setHttpMethod(HttpMethod mHttpMethod) {
        this.mHttpMethod = mHttpMethod;
        return this;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public Request<T> setPriority(Priority mPriority) {
        this.mPriority = mPriority;
        return this;
    }

    public RequestListener<T> getListener() {
        return mListener;
    }

    public Request<T> setListener(RequestListener<T> mListener) {
        this.mListener = mListener;
        return this;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Request<T> setHeaders(Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
        return this;
    }

    public Map<String, String> getBody() {
        return mBody;
    }

    public Request<T> setBody(Map<String, String> mBody) {
        this.mBody = mBody;
        return this;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public Request<T> setCancel(boolean cancel) {
        isCancel = cancel;
        return this;
    }

    public boolean isCache() {
        return isCache;
    }

    public boolean isHttps(){
        return mUrl.contains("https://");
    }

    public Request<T> setCache(boolean cache) {
        isCache = cache;
        return this;
    }

    public Request<T> addHeader(String key,String value){
        mHeaders.put(key,value);
        return this;
    }

    public Request<T> addBody(String key,String value){
        mBody.put(key,value);
        return this;
    }

    //解析响应数据,用户实现
    protected abstract T parseResponse(Response response);

    //会由UI线程的handler抛到UI线程中运行
    public final void deliveryResponse(Response response){
        T result = parseResponse(response);
        if(mListener != null ){
            int statusCode = response != null ? response.getStatusCode() : -1;
            String message = response != null ? response.getMessage() : "unkown error";
            mListener.onComplete(statusCode,result,message);
        }
    }


    //请求方法
    public static enum HttpMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String mHttpMethod = "";

        private HttpMethod(String method) {
            mHttpMethod = method;
        }

        @Override
        public String toString() {
            return mHttpMethod;
        }
    }

    //请求优先级
    public static enum Priority{
        LOW,
        NORMAL,
        HIGN,
        IMMEDIATE
    }

    //请求结果的回调
    public static interface RequestListener<T> {
        public void onComplete(int stCode, T response, String errMsg);
    }
}
