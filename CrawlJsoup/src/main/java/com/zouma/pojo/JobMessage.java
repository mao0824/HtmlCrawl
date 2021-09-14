package com.zouma.pojo;

import lombok.*;

/**
 * @Author:zouma
 * @Date:2021/9/13 23:22
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class JobMessage {

    // 职位名称
    private String jobName;
    // 公司名称
    private String companyName;
    // 薪水
    private String salary;
    // 公司位置
    private String location;
    // 工作经验
    private String workExperience;
    // 学历
    private String education;
    // 行业
    private String industry;
    // 公司规模
    private String companySize;

}
