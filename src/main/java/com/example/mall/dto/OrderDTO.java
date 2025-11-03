package com.example.mall.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Integer totalAmount;//訂單總金額
    private String recipientName;//收件人姓名
    private String recipientPhone;//收件人電話
    private String recipientAddress;//收件人地址
}
