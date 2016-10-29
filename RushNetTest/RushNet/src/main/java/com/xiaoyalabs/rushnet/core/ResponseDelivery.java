package com.xiaoyalabs.rushnet.core;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhanghongjun on 2016/10/29.
 *
 * 响应结果分发类
 *
 */

public class ResponseDelivery {
    Handler uiHandler = new Handler(Looper.getMainLooper());

    public final void deliveryResponse(final Request<?> request,final Response response){
        Runnable responseRunnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };

        uiHandler.post(responseRunnable);
    }

}
