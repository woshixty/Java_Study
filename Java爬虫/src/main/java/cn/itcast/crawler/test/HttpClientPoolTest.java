package cn.itcast.crawler.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/17
 * httpClientPool 的学习与使用
 * 连接池
 **/
public class HttpClientPoolTest {

    public static void main(String[] args) {

        //创建连接池管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        //设置连接数
        //设置最大连接数
        cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);

        //使用连接池管理器发起请求
        //每次的HttpClient都不一样
        doGet(cm);
        doGet(cm);

    }

    private static void doGet(PoolingHttpClientConnectionManager cm) {
        //不是每次创建新的httpClient对象，而是从连接池中获取对象
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("www.itcast.cn/");

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf8");
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
