package com.pang.ucenter.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 日志记录

    private static RequestConfig requestConfig = null;

    static
    {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    }


    public static String get(String url)
    {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try
        {
            CloseableHttpResponse response = client.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
            }
            else
            {
                logger.error("get请求提交失败:" + url);
            }
        }
        catch (IOException e)
        {
            logger.error("get请求提交失败:" + url, e);
        }
        finally
        {
            request.releaseConnection();
        }
        return jsonResult.toString();
    }

}

