package com.xty.demo.ImageUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/9
 **/

@RestController
@RequestMapping("/xty")
@Slf4j
public class TestController {

    @GetMapping("/hello")
    public void helloworld() {
        System.out.println("hello world");
    }

    @PostMapping("/picture")
    public ResultVO pic(@RequestParam("head_pic") MultipartFile multipartFile) {

        String filedir="/Users/mr.stark/Desktop/vedios";
        File file = new File(filedir);

        String originalFileName = multipartFile.getOriginalFilename();
//        System.out.println(originalFileName);
        String suffix = null;
        if (originalFileName != null) {
            suffix=originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String newFileName= UUID.randomUUID()+suffix;
        log.info(newFileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String returnURL=new String("/Users/mr.stark/Desktop/vedios/"+newFileName);
        return ResultVOUtile.success(returnURL);
    }
}
