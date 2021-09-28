package com.zouma.controller;

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

    @GetMapping("/1")
    @ResponseBody
    public String test() throws IOException {

        jobMessageServer.messageList();
        return "成功";
    }

}
