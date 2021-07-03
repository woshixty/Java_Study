package com.xty.CosCloud;

import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import org.springframework.mock.web.MockMultipartFile;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/10
 **/
public class filetest {

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/mr.stark/Desktop/picture/picture.png";
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        // MockMultipartFile
        // (String name, @Nullable String originalFilename, @Nullable String contentType, InputStream contentStream)
        // 其中originalFilename,String contentType 旧名字，类型  可为空
        // ContentType.APPLICATION_OCTET_STREAM.toString() 需要使用HttpClient的包

        MultipartFile multipartFile = new MockMultipartFile("copy"+file.getName(),
                file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(),
                fileInputStream);
        System.out.println(multipartFile.getName());
    }
}
