package com.zouma.server;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zouma.pojo.JobMessage;
import com.zouma.pojo.From;
import com.zouma.utils.DocumentTool;
import org.apache.http.client.protocol.HttpClientContext;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public void messageList(From from) throws IOException {

        int startPage = from.getStartPage();
        int endPage = from.getEndPage();
        List<JobMessage> jobMessageList = new ArrayList();
        if (startPage < 1){
            startPage = 1;
        }
        if (endPage < 1){
            endPage = 1;
        }

        // https://www.zhipin.com/i101303-c100010000/?query=%E6%80%BB%E7%BB%8F%E7%90%86&page=2&ka=page-2
        for (int j = startPage; j <= endPage ; j++) {
            String searchUrl = from.getUrl()+"&page="+ j+"&ka=page-"+ j;
            System.out.println("搜索的地址：------>" + searchUrl);
            String cookie = from.getCookie();
            HttpClientContext httpContext = HttpClientContext.create();
            Document d1 = DocumentTool.currRequest(httpContext, searchUrl, cookie);
            Elements elements = d1.getElementsByClass("job-primary");
            // System.out.println("Elements---->" + elements);

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
                List<String> collect = Arrays.stream(split).map(c -> c.replace("<p>", "").replace(
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
        }

        System.out.println("jobMessageList-------->" + jobMessageList.size());

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();

        File f = File.createTempFile("Boss", ".xls");

        ArrayList<JobMessage> rows = CollUtil.newArrayList(jobMessageList);


        // 设置标题别名
        writer.addHeaderAlias("jobName", "职位名称");
        writer.addHeaderAlias("companyName", "公司名称");
        writer.addHeaderAlias("salary", "薪水");
        writer.addHeaderAlias("location", "公司位置");
        writer.addHeaderAlias("workExperience", "工作经验");
        writer.addHeaderAlias("education", "学历");
        writer.addHeaderAlias("industry", "行业");
        writer.addHeaderAlias("companySize", "公司规模");

        writer.setColumnWidth(0,30);
        writer.setColumnWidth(1,30);
        writer.setColumnWidth(2,30);
        writer.setColumnWidth(3,30);
        writer.setColumnWidth(4,30);
        writer.setColumnWidth(5,30);
        writer.setColumnWidth(6,30);
        writer.setColumnWidth(7,30);

        writer.write(rows, true);

        FileOutputStream out = new FileOutputStream(f);

        writer.flush(out);

        // 关闭writer，释放内存
        writer.close();

        // 发送邮件
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("1115772640@qq.com");
        account.setUser("1115772640");
        account.setPass("djqwzcyibdmbbaff");

        MailUtil.send(account,"1028647294@qq.com", "boss数据", "老板，来邮件了", false,f);
        // 1028647294@qq.com  460105167@qq.com
    }

}
