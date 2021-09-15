package com.zouma.server;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zouma.pojo.JobMessage;
import com.zouma.utils.DocumentTool;
import org.apache.http.client.protocol.HttpClientContext;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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
    public void messageList(HttpServletResponse response,String cookie) throws IOException {
        ArrayList<JobMessage> jobMessageList = new ArrayList();

        String url = "https://www.zhipin.com/job_detail/?query=%E9%87%87%E8%B4%AD&city=101120800&industry=&position=1";
        HttpClientContext httpContext = HttpClientContext.create();
        Document d1 = DocumentTool.currRequest(httpContext, url, cookie);
        System.out.println(d1);
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


        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        ArrayList<JobMessage> rows = CollUtil.newArrayList(jobMessageList);
        String fileName = "Boss信息.xls";

        // 设置标题别名
        writer.addHeaderAlias("jobName", "职位名称");
        writer.addHeaderAlias("companyName", "公司名称");
        writer.addHeaderAlias("salary", "薪水");
        writer.addHeaderAlias("location", "公司位置");
        writer.addHeaderAlias("workExperience", "工作经验");
        writer.addHeaderAlias("education", "学历");
        writer.addHeaderAlias("industry", "行业");
        writer.addHeaderAlias("companySize", "公司规模");

        writer.write(rows, true);

        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));

        ServletOutputStream out=response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

}
