package com.xiaoyalabs.rushnet;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by zhanghongjun on 2016/10/29.
 * 网络请求执行者
 */

public class NetworkExecutor extends Thread {
    //网络请求者
    HttpStack httpStack = null;

    //请求队列
    BlockingQueue<Request<?>> requestQueue;

    //响应结果分发者
    ResponseDelivery responseDelivery = new ResponseDelivery();

    private static Cache<String, Response> mResponseCache = new LruMemCache();

    //线程标志
    boolean isStop = false;


    public NetworkExecutor(BlockingQueue<Request<?>> requestQueue, HttpStack httpStack) {
        this.requestQueue = requestQueue;
        this.httpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                final Request<?> request = requestQueue.take();
                if(request.isCancel){
                    continue;
                }

                Response response = null;
                //是否从缓存里面取
                if (isUseCache(request)) {
                    responseDelivery.deliveryResponse(request, mResponseCache.get(request.getUrl()));
                } else {
                    response = httpStack.performRequest(request);
                    if (request.isCache() && isSuccess(response)) {
                        mResponseCache.put(request.getUrl(), response);
                    }

                    responseDelivery.deliveryResponse(request, response);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //退出
    public void quit(){

    }

    //是否要缓存起来
    private boolean isSuccess(Response response) {
        return response != null && response.getStatusCode() == 200;
    }

    //是否需要从缓存里面取
    private boolean isUseCache(Request request) {
        return request.isCache() && mResponseCache.get(request.getUrl()) != null;
    }

}





