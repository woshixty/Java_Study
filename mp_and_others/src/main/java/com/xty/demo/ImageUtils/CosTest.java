package com.xty.demo.ImageUtils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.util.UUID;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/10
 **/

@RestController
@RequestMapping("/cos")
@Slf4j
public class CosTest {
    private String secretId="AKIDPGuLWSSbnjU1OVH6ZlhPs76sOe7LizDL";
    private String secretKey="3EBpGVdaIl48yYtypPkrhHcV8UaR7Ypr";

    @GetMapping("/hello")
    public void helloworld() {
        System.out.println("hello world");
    }


    @PostMapping("/pic")
    public ResultVO pic(@RequestParam("head_pic") MultipartFile multipartFile) throws IOException {

        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = null;
        if (originalFileName != null) {
            suffix=originalFileName.substring(originalFileName.lastIndexOf("."));
        }

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



//        //查询所有的存储桶
//        List<Bucket> buckets = cosClient.listBuckets();
//        for (Bucket bucketElement : buckets) {
//            String buckentName = bucketElement.getName();
//            String buckentLocation = bucketElement.getLocation();
//            System.out.println(buckentName + " and " + buckentLocation);
//        }


        /*

        //你在看这个代码的时候会觉得很熟悉，是的这个就是File转MultipartFile的逆转过程，这个方法会在根目录生成一个文件，需要删除该文件。
        int n;
        //得到multipartfile文件
        File file=null;

        //输出文件的新name 指上传后的文件名称
        System.out.println("getName" + multipartFile.getName());

        //输出源文件名称
        System.out.println("oriName" + multipartFile.getOriginalFilename());

        //创建文件
        file = new File(multipartFile.getOriginalFilename());

        try (InputStream in  = multipartFile.getInputStream(); OutputStream os = new FileOutputStream(file)) {

            // 得到文件流。以文件流的方式输出到新文件
            // 可以使用byte[] ss = multipartFile.getBytes();代替while

            byte[] buffer = new byte[4096];
            while ( (n=in.read(buffer, 0, 4096)) != -1) {
                os.write(buffer, 0, n);
            }

            // 读取文件第一行
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//            System.out.println(bufferedReader.readLine());
//            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 输出file的URL
        log.info(file.toURI().toURL().toString());

        // 输出文件的绝对路径
        log.info(file.getAbsolutePath());


         */

        File file=new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);

        // 指定要上传的文件
//        File localFile = new File(localFilePath);
        // 指定要上传到的存储桶
        String bucketName = "cos-1301609895";
        // 指定要上传到 COS 上对象键
        String key = UUID.randomUUID()+suffix;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        // 操作完上的文件 需要删除在根目录下生成的文件
        File f = new File(file.toURI());
        if ( f.delete() ){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }

        cosClient.shutdown();
        return ResultVOUtile.success("");
    }
}
