package com.example.mall.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private Integer id;//訂單id
    private Integer userId;//用戶ID
    private Integer totalAmount;//訂單總金額
    private String status;//訂單狀態
    private String recipientName;//收件人姓名
    private String recipientPhone;//收件人電話
    private String recipientAddress;//收件人地址

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private LocalDateTime createdTime;//訂單創建時間
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private LocalDateTime updatedTime;//訂單修改時間

}
