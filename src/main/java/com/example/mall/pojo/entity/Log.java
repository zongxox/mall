package com.example.mall.pojo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Log {
    private long id;
    private String ip;
    private String username;
    private Date createdTime;
    private String operation;
    private String method;
    private String params;
    private long time;
    private long status;
    private String error;

}
