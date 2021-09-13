package com.zouma.server;

import com.zouma.utils.DocumentTool;
import org.apache.http.client.protocol.HttpClientContext;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * @Author:zouma
 * @Date:2021/9/14 0:08
 * @Version 1.0
 */
@Service
public class JobMessageServerImpl implements JobMessageServer {

    public Elements messageList(){
        String url = "https://www.zhipin.com/job_detail/?query=%E9%87%87%E8%B4%AD&city=101120800&industry=&position=1";
        HttpClientContext httpContext = HttpClientContext.create();
        String cookie = "lastCity=101010100; __g=-; __zp_seo_uuid__=0a72e9eb-0eec-40a7-86b0-5ff4c5723e1d; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1631284985,1631285743,1631546660,1631546712; acw_tc=0bdd343a16315499213646394e019ad187562b217069f477eba1516d51d84f; __l=r=https%3A%2F%2Fwww.bosszhipin.com%2F&l=%2Fwww.zhipin.com%2Fjob_detail%2F%3Fquery%3D%25E9%2587%2587%25E8%25B4%25AD%26city%3D101120800%26industry%3D%26position%3D1&s=3&friend_source=0&s=3&friend_source=0; ___gtid=1799621583; __fid=2242e3cfa7b604a3baa8eccfe9f54d05; __zp_stoken__=a414caWEhUnd1U1w%2BDhtLN2NwIBE6cylJfGIMJV9iQCoZD2ZkdElBb39tZAImV01xWjtSJm4oNxNxBkdlXBpHNlEBYV1rMR0mZxo6CWEjWWx3KBxXbhcxOH0EeBJdHBQYahhCNU4tQ2cNS11F; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1631549984; __c=1631546661; __a=89413230.1630933486.1631285743.1631546661.122.7.8.122";
        Document d1 = DocumentTool.currRequest(httpContext, url, cookie);
        Elements elements = d1.getElementsByClass("job-primary");
        return elements;
    }


}
