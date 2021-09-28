package com.zouma.server;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:zouma
 * @Date:2021/9/14 0:22
 * @Version 1.0
 */
public interface JobMessageServer {

     void messageList() throws IOException;
}
