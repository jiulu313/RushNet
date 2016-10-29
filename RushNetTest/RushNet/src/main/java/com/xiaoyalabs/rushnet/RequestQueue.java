package com.xiaoyalabs.rushnet;

import com.xiaoyalabs.rushnet.core.NetworkExecutor;
import com.xiaoyalabs.rushnet.core.Request;
import com.xiaoyalabs.rushnet.httpStack.HttpStack;
import com.xiaoyalabs.rushnet.httpStack.HttpStackFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhanghongjun on 2016/10/29.
 */

public class RequestQueue {
    //请求队列
    private BlockingQueue<Request<?>> requestQueue = new PriorityBlockingQueue<Request<?>>();

    //请求的系列号
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    //线程数据 （CPU核心数+1）
    public static final int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    //核心数
    private int mDispatcherNums = DEFAULT_CORE_NUMS;

    //网络请求执行者
    private NetworkExecutor[] mNetworkExecutors = null;

    //网络请求执行者
    private HttpStack httpStack;


    public RequestQueue(int coreNumber, HttpStack httpStack) {
        if (coreNumber > 0) {
            this.mDispatcherNums = coreNumber;
        }
        this.httpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
        mNetworkExecutors = new NetworkExecutor[mDispatcherNums];
        for (int i = 0; i < mDispatcherNums; i++) {
            mNetworkExecutors[i] = new NetworkExecutor(requestQueue, httpStack);
        }
    }

    public void start() {
        stop();
        startNetworkExecutors();
    }

    public void stop() {
        for (int i = 0; i < mDispatcherNums; i++) {
            mNetworkExecutors[i].quit();
        }
    }

    private void startNetworkExecutors() {
        for (int i = 0; i < mDispatcherNums; i++) {
            mNetworkExecutors[i].start();
        }
    }

    //添加一个请求
    public void addRequest(Request<?> request){
        if(!requestQueue.contains(request)){
            request.setSerialNumber(generateSerialNumber());
            requestQueue.add(request);
        }
    }

    //清空请求队列
    public void clear(){
        requestQueue.clear();
    }

    //获取所有的请求
    public BlockingQueue<Request<?>> getAllRequests(){
        return requestQueue;
    }

    //获取
    private int generateSerialNumber(){
        return mSerialNumGenerator.incrementAndGet();
    }

}
