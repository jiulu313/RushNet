package com.xiaoyalabs.rushnet;


import android.support.v4.util.LruCache;

/**
 * Created by zhanghongjun on 2016/10/28.
 */

public interface Cache<K,V> {
    void put(K key,V value);
    V get(K key);
    void remove(K key);

}
