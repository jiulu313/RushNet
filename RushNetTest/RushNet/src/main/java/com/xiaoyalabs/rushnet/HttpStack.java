package com.xiaoyalabs.rushnet;

import android.app.DownloadManager;

/**
 * Created by zhanghongjun on 2016/10/29.
 */

public interface HttpStack {
    //网络请求
    Response performRequest(Request<?> request);
}
