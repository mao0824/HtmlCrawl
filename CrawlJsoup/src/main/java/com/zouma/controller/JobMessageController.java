package com.zouma.controller;

import com.zouma.pojo.From;
import com.zouma.server.JobMessageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author:zouma
 * @Date:2021/9/14 0:07
 * @Version 1.0
 */
@RestController
public class JobMessageController {

    @Resource
    private JobMessageServer jobMessageServer;

    @PostMapping("/bossRequestInfo")
    public String test(@RequestBody From from) throws IOException {

        jobMessageServer.messageList(from);
        return "成功";
    }

}
