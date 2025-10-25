package com.example.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Boolean isAdmin; //一般會員=0 管理員=1
    private String resetToken; // 忘記密碼驗證 token

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private LocalDateTime resetTokenExpire; // 忘記密碼驗證 token 的過期時間

    private String emailVerifyToken; //email註冊驗證用 token

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private LocalDateTime emailVerifyTokenExpire; //email驗證 token 過期時間

    private Boolean emailVerified; //email是否已驗證 0=未驗證, 1=已驗證

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")  // 使用 @JsonFormat 來格式化返回的日期
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")  // 使用 @JsonFormat 來格式化返回的日期
    private LocalDateTime updatedTime;
}
