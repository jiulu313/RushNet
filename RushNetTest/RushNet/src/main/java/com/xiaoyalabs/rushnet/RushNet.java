package com.xiaoyalabs.rushnet;

import com.xiaoyalabs.rushnet.httpStack.HttpStack;

/**
 * Created by zhanghongjun on 2016/10/29.
 */

public class RushNet {

    public static RequestQueue newRequestQueue(){
        return newRequestQueue(RequestQueue.DEFAULT_CORE_NUMS);
    }

    public static RequestQueue newRequestQueue(int coreNum){
        return newRequestQueue(coreNum,null);
    }

    public static RequestQueue newRequestQueue(int coreNum, HttpStack httpStack){
        RequestQueue queue = new RequestQueue(coreNum,httpStack);
        queue.start();
        return queue;
    }
}
