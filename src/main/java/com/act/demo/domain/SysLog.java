package com.act.demo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SysLog {
    private Integer id;

    private Integer level;

    private String user;

    private String ip;

    private Date logTime;

    private String source;

    private String browser;

    private String platform;

    private String content;

}