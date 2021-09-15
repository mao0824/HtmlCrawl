package com.zouma;


import com.zouma.server.JobMessageServer;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrawlJsoupApplicationTests {

    @Autowired
    private JobMessageServer jobMessageServer;

    @Test
    void contextLoads() {

    }

}
