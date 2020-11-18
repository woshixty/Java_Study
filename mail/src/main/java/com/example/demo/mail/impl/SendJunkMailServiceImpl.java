package com.example.demo.mail.impl;

import com.example.demo.mail.SendJunkMailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/9/21
 **/
@Service
public class SendJunkMailServiceImpl implements SendJunkMailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String form;

    public static final Logger LOGGER= (Logger) LogManager.getLogger(SendJunkMailServiceImpl.class);

    @Override
    public boolean sendJunkMail() {

        try {
            List<String> list = new ArrayList<>();
            list.add("hello");
            list.add("world");
            list.add("im xty");

            for (String s:list) {

                SimpleMailMessage message=new SimpleMailMessage();
                message.setFrom(form);
                message.setSubject("Just Have A Test");
                message.setTo("qyyzxty@icloud.com");
                message.setText("Hello this is a test mail");
                javaMailSender.send(message);
                LOGGER.info("YES");
            }

        } catch (Exception e) {
            LOGGER.error("send mail error");
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
