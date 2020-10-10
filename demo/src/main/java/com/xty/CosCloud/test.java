package com.xty.CosCloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/10
 **/
public class test {

    public static void main(String[] args) {

        String secretId="";
        String secretKey="";

        //初始化用户身份信息
        COSCredentials credentials = new BasicCOSCredentials(secretId, secretKey);

        // 2 设置 bucket 的区域, COS 地域的简称请参照
        // https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法,
        // 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(credentials, clientConfig);


//        //以下为创建一个存储桶
//        String bucket = "cos-1301609895";  //存储桶名称，格式：BucketName-APPID
//        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
//        // 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
//        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
//        try {
//            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
//        } catch (CosServiceException serviceException) {
//            serviceException.printStackTrace();
//        } catch (CosClientException cosClientException) {
//            cosClientException.printStackTrace();
//        }

        String bucket = "cos-1301609895";

        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String buckentName = bucketElement.getName();
            String buckentLocation = bucketElement.getLocation();
            System.out.println(buckentName + " and " + buckentLocation);
        }

        String filePath = "/Users/mr.stark/Desktop/picture/picture.png";
        File file = new File(filePath);
        String key = "exampleobject.png";

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

    }
}
