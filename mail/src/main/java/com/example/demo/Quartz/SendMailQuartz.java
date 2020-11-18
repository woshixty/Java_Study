package com.example.demo.Quartz;

import com.example.demo.mail.SendJunkMailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author qyyzxty@icloud.com
 * @data 2020/9/21
 **/

@Component
@Configurable
@EnableScheduling
public class SendMailQuartz {

    @Resource
    SendJunkMailService sendJunkMailService;

    private static final Logger LOGGER= (Logger) LogManager.getLogger(SendMailQuartz.class);

    @Scheduled(cron = "*/5 * * * * * ")
    public void reportCurrentByCron() {
        sendJunkMailService.sendJunkMail();
        LOGGER.info("Hello Quartz ! ! !");
    }
}
