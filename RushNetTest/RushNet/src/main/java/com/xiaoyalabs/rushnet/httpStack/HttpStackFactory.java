package com.xiaoyalabs.rushnet.httpStack;

import android.os.Build;

/**
 * Created by zhanghongjun on 2016/10/29.
 */

public class HttpStackFactory {

    // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
    private static final int GINGERBREAD_SDK_NUM = 9;

    public static HttpStack createHttpStack() {
        int runtimeSDKApi = Build.VERSION.SDK_INT;
        if (runtimeSDKApi >= GINGERBREAD_SDK_NUM) {
            return new HttpUrlConnStack();
        }

        return new HttpClientStack();
    }

}
