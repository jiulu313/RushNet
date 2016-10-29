package com.xiaoyalabs.rushnet.cache;

import android.support.v4.util.LruCache;

import com.xiaoyalabs.rushnet.core.Response;

/**
 * Created by zhanghongjun on 2016/10/28.
 */

public class LruMemCache implements Cache<String,Response>{
    private LruCache<String, Response> mResponseCache = null;

    public LruMemCache(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mResponseCache = new LruCache<String,Response>(cacheSize){
            @Override
            protected int sizeOf(String key, Response response) {
                return response.getRawDataLength() / 1024;
            }
        };
    }

    @Override
    public void put(String key, Response response) {
        mResponseCache.put(key,response);
    }

    @Override
    public Response get(String key) {
        return mResponseCache.get(key);
    }

    @Override
    public void remove(String key) {
        mResponseCache.remove(key);
    }
}
