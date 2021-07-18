package cn.itcast.crawler.test;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/16
 **/

public class HttpPostParamTest {

    public static void main(String[] args) throws UnsupportedEncodingException {

        //创建HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建HttpPost对象，并设置url
        HttpPost httpPost = new HttpPost("http://www.itcast.cn/");

        //声明list集合，封装表单中的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        //设置请求地址为http://yun.itheima.com/search?keys=Java
        params.add(new BasicNameValuePair("keys", "Java"));

        //创建表单的entity对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf8");

        //设置表单中的entity数据到post请求中
        httpPost.setEntity(formEntity);

        //声明response
        CloseableHttpResponse response = null;
        try {

            //使用HttpClient发起请求，获取response
            response = httpClient.execute(httpPost);

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
