package com.zouma.server;

import com.zouma.pojo.From;

import java.io.IOException;

/**
 * @Author:zouma
 * @Date:2021/9/14 0:22
 * @Version 1.0
 */
public interface JobMessageServer {

     void messageList(From from) throws IOException;
}
