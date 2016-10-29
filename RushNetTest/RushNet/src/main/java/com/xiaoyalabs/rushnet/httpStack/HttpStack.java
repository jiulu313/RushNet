package com.xiaoyalabs.rushnet.httpStack;

import com.xiaoyalabs.rushnet.core.Request;
import com.xiaoyalabs.rushnet.core.Response;

/**
 * Created by zhanghongjun on 2016/10/29.
 */

public interface HttpStack {
    //网络请求
    Response performRequest(Request<?> request);
}
