package cn.itcast.crawler.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/16
 * 带参数的爬虫
 **/

public class HttpGetParamTest {

    public static void main(String[] args) throws URISyntaxException {

        //创建HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置请求地址为http://yun.itheima.com/search?keys=Java

        /**
         * 有参数的网络爬虫
         */
        //创建UriBuilder
        URIBuilder uriBuilder = new URIBuilder("http://yun.itheima.com/search");
        //设置参数
        uriBuilder.addParameter("keys", "Java");

        //创建HttpGet对象，并设置url
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        //发起请求的信息
        System.out.println(httpGet);

        //声明response
        CloseableHttpResponse response = null;
        try {

            //使用HttpClient发起请求，获取response
            response = httpClient.execute(httpGet);

            //解析响应
            if(response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf8");
                System.out.println(content.length());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //关闭client
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
