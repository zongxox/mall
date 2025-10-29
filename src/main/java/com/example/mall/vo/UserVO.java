package com.example.mall.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Integer id;
    private String account;
    private String name;
    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_time;
    private Boolean is_admin;
}
