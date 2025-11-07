package com.example.mall.entity;

import lombok.Data;

@Data
public class OrderItem {
    private Integer id;                // 訂單項目ID
    private Integer orderId;           // 所屬訂單ID（外鍵）
    private Integer productVariantId;  // 商品規格ID（外鍵）
    private Integer quantity;          // 購買數量
    private Integer price;             //單價
}
