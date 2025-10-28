package com.example.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CartItems {
    private Integer id;
    private Integer userId;
    private Integer productVariantId;
    private Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")  // 使用 @JsonFormat 來格式化返回的日期
    private LocalDateTime createdTime;
}
