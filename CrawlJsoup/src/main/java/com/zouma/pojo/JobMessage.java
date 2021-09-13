package com.zouma.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:zouma
 * @Date:2021/9/13 23:22
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobMessage {

    private String jobName;
    private String salary;
    private String location;
    private String workRequire;
    private String edu;
    private String BusNature;
    private String isShangShi;
    private String guiMo;
    private String fabuShijian;
    private String zhiweiXiangqing;
    private String gongsixiangqing;
    private String ComName;

}
