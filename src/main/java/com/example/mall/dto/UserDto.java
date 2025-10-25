package com.example.mall.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String reset_token; // 忘記密碼驗證 token
    private String emailVerifyToken; //註冊驗證用 token
}
