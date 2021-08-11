package com.xty.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/8/5
 **/
public class ESTest_Client {
    public static void main(String[] args) throws IOException {
        //创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200))
        );
        //关闭ES客户端
        esClient.close();
    }
}
