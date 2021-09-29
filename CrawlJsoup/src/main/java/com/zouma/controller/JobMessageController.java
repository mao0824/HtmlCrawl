package com.zouma.controller;

import com.zouma.pojo.from;
import com.zouma.server.JobMessageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;

/**
 * @Author:zouma
 * @Date:2021/9/14 0:07
 * @Version 1.0
 */
@Controller
public class JobMessageController {

    @Autowired
    private JobMessageServer jobMessageServer;

    @PostMapping("/bossRequestInfo")
    @ResponseBody
    public String test(@RequestBody from from) throws IOException {

        jobMessageServer.messageList(from);
        return "成功";
    }

}
