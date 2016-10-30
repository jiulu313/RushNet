package com.xiaoyalabs.rushnet.httpStack;

import com.xiaoyalabs.rushnet.core.Request;
import com.xiaoyalabs.rushnet.core.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.ReferenceQueue;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongjun on 2016/10/29.
 */

public class HttpUrlConnStack implements HttpStack {
    @Override
    public Response performRequest(Request<?> request) {

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = createURLConnection(request);
            setRequestHeader(urlConnection, request);
            setRequestParams(urlConnection, request);
            return fetchResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private Response fetchResponse(HttpURLConnection connection) throws IOException {

//        // Initialize HttpResponse with data from the HttpURLConnection.
//        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
//        int responseCode = connection.getResponseCode();
//        if (responseCode == -1) {
//            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
//        }
//        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
//                connection.getResponseCode(), connection.getResponseMessage());
//        Response response = new Response(responseStatus);
//        response.setEntity(entityFromURLConnwction(connection));
//        addHeadersToResponse(response, connection);
//        return response;


        return null;
    }

    private void setRequestParams(HttpURLConnection urlConnection, Request<?> request) throws IOException {
        Request.HttpMethod httpMethod = request.getHttpMethod();
        //设置请求方法
        urlConnection.setRequestMethod(httpMethod.toString());

        //添加参数
        byte[] body = request.getBody();
        //enable output
        urlConnection.setDoOutput(true);

        urlConnection.addRequestProperty(Request.HEADER_CONTENT_TYPE, request.getBodyContentType());
        DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
        dos.write(body);
        dos.close();
    }

    //设置请求头部
    private void setRequestHeader(HttpURLConnection urlConnection, Request<?> request) {
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    //创建连接
    private HttpURLConnection createURLConnection(Request<?> request) throws IOException {
        URL url = new URL(request.getUrl());
        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(request.getTimeOut());
        urlConnection.setReadTimeout(request.getTimeOut());
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        return (HttpURLConnection) urlConnection;
    }


}











