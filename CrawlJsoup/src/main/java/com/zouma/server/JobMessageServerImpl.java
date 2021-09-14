package com.zouma.server;

import com.zouma.pojo.JobMessage;
import com.zouma.utils.DocumentTool;
import org.apache.http.client.protocol.HttpClientContext;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:zouma
 * @Date:2021/9/14 0:08
 * @Version 1.0
 */
@Service
public class JobMessageServerImpl implements JobMessageServer {

    @Override
    public Elements messageList() {
        ArrayList<JobMessage> jobMessageList = new ArrayList();

        String url = "https://www.zhipin.com/job_detail/?query=%E9%87%87%E8%B4%AD&city=101120800&industry=&position=1";
        HttpClientContext httpContext = HttpClientContext.create();
        String cookie = "lastCity=101010100; __fid=2242e3cfa7b604a3baa8eccfe9f54d05; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1631285743,1631546660,1631546712,1631627976; __g=-; acw_tc=0bcb2f0916316344750244221efe588b5f423533508f6e5bda6c0b3b69040d; __zp_stoken__=f9a3caSw8PS1bNyN%2FHXQ%2FKSEuG3VTSV1BTllcWQ13LG41MjIvJy9MNUpAQV54aTY8Fx8JJmRNfl9Ca0UBdQ4gXQkQTiwSTwAWbiEHHSpJPwlMR0dDYFFIOjYpIxZ3AjJBJzwZNUQ%2FVl1HdXkh; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1631636233; __c=1631627976; __l=l=%2Fwww.zhipin.com%2Fjob_detail%2F%3Fquery%3D%25E9%2587%2587%25E8%25B4%25AD%26city%3D101120800%26industry%3D%26position%3D1&r=&g=&s=3&friend_source=0&s=3&friend_source=0; __a=89413230.1630933486.1631546661.1631627976.130.8.7.130";
        Document d1 = DocumentTool.currRequest(httpContext, url, cookie);
        Elements elements = d1.getElementsByClass("job-primary");

        for (Element e :
                elements) {
            JobMessage jobMessage = new JobMessage();

            // 职位名称
            jobMessage.setJobName(e.select("div div .job-title span a").text());
            // 公司名称
            jobMessage.setCompanyName(e.select(".company-text h3").text());
            // 薪水
            jobMessage.setSalary(e.select(".red").text());
            // 公司位置
            jobMessage.setLocation(e.select("span .job-area").text());

            String[] split = e.select("div .job-limit").select(".clearfix p").toString().split(
                    "<em class=\"vline\"></em>");
            List<String> collect = Arrays.stream(split).map(i -> i.replace("<p>", "").replace(
                    "</p>", "")).collect(Collectors.toList());
            // 工作经验
            jobMessage.setWorkExperience(collect.get(0));
            // 学历
            jobMessage.setEducation(collect.get(1));
            // 行业
            jobMessage.setIndustry(e.select("div .company-text p a").text());
            // 公司规模
            jobMessage.setCompanySize(e.select("div .company-text p").text().replace(e.select(
                    "div .company-text p a").text(), ""));
            jobMessageList.add(jobMessage);
        }

        return elements;
    }


}
