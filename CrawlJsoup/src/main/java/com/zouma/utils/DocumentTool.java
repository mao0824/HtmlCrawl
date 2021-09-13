package com.zouma.utils;


import com.google.common.base.Preconditions;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author:zouma
 * @Date:2021/9/13 23:45
 * @Version 1.0
 */
public class DocumentTool {

    private static final Logger logger = LoggerFactory.getLogger(DocumentTool.class);
    private static final CloseableHttpClient http;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(3);
        cm.setDefaultMaxPerRoute(3);
        http = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    public static Document currRequest(HttpClientContext httpContext, String url, String cookie) {

        HttpUriRequest req = RequestBuilder.get()
                .setUri(url)
                .setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .setHeader("accept-encoding", "gzip, deflate, br")
                .setHeader("accept-language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                .setHeader("cache-control", "max-age=0")
                .setHeader("cookie", cookie)
                .setHeader("Host", "www.zhipin.com")
                .setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36")
                .build();

        try (CloseableHttpResponse response = http.execute(req, httpContext)) {

            logger.info("{}", req.getURI());
            Preconditions.checkArgument(response.getStatusLine().getStatusCode() == 200, response);
            Thread.sleep(3000);
            try (InputStream is = response.getEntity().getContent()) {
                return Jsoup.parse(is, "utf-8", "");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
