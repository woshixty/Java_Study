package com.example.xty.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传测试
 */
@Controller
@Slf4j
public class FormTestController {

    @GetMapping("/form_layouts")
    public String form_layouts() {
        return "form/form_layouts";
    }

    /**
     * MultipartFile自动封装上传来的文件
     * @param email
     * @param username
     * @param headerImg
     * @param photos
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("email") String email,
                         @RequestParam("username") String username,
                         @RequestPart("headerImg") MultipartFile headerImg,
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {
        System.out.println(email);
        System.out.println(username);
        System.out.println(headerImg.getSize());
        System.out.println(photos.length);
        if (!headerImg.isEmpty()) {
            //保存到文件服务器、oss服务器
//            String originalFilename = headerImg.getOriginalFilename();
            headerImg.transferTo(new File("/Users/xietingyu/Desktop/cache/" + "1.jpg"));
        }

        if (photos.length > 0) {
            int i = 2;
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    //保存到文件服务器、oss服务器
                    photo.transferTo(new File("/Users/xietingyu/Desktop/cache/" + i + ".jpg" ));
                    i++;
                }
            }
        }
        return "index";
    }
}
