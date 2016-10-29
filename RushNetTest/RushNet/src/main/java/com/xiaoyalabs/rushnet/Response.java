package com.xiaoyalabs.rushnet;

/**
 * Created by zhanghongjun on 2016/10/27.
 */

public class Response {
    protected byte[] mRawData = new byte[0];


    public int getStatusCode(){
        return 0;
    }

    public String getMessage(){
        return "";
    }

    public int getRawDataLength(){
        return mRawData.length;
    }

}
