package com.xty.demo.ImageUtils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImageUtil {

    public static File upload(MultipartFile multipartFile) {

        //指定要上传文件目录
        String fileDir = "/lhonline";

        File dir = new File(fileDir);

        //判断这个目录是否存在


        //生成新的文件名，防止原文件被覆盖
        //1、先获取原文件名，获取原文件后缀名
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = null;
        if (originalFileName != null) {
            suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        //2、使用uuid生成文件名
        String newFileName = UUID.randomUUID() + suffix;

        //生成文件，文件已经生成但是没有内容
        File file = new File(dir, newFileName);

        System.out.println(dir);

        //3、传输文件内容
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
