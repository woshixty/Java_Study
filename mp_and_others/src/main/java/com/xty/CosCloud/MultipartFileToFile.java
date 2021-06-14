package com.xty.CosCloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/10
 **/
public class MultipartFileToFile {
    public File transform(MultipartFile multipartFile) {
        //使用transferTo (本质上还是使用了流 只不过是封装了步骤)
        //会生成文件，最后不需要文件要删除
        return null;
    }

    public static void main(String[] args) {

    }
}
