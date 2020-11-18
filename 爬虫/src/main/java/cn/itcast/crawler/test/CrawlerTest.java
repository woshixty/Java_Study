package cn.itcast.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/16
 * 爬虫其实就类似于我们平常上网的操作
 **/
public class CrawlerTest {

    public static void main(String[] args) throws Exception {

        //1.打开浏览器，创建HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.输入网址,发起get请求创建httpGet对象
        /**
         * HttpPost;
         * HttpPut;
         * HttpDelete;
         */
        HttpGet httpGet = new HttpGet("http://www.itcast.cn/");

        //3.按下回车，发起请求，响应数据，使用httpClient对象发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //4.解析响应，获取数据

        //判断状态码
        if( response.getStatusLine().getStatusCode() == 200 ) {

            //获取响应体
            HttpEntity httpEntity = response.getEntity();

            //解析响应体
            String content = EntityUtils.toString(httpEntity, "utf8");

            //打印输出康康
            System.out.println(content);
        }

    }
}
