package com.example.mall.dto;

import lombok.Data;

@Data
public class CartItemsDTO {
    private Integer userId;
    private Integer id;
    private Integer productVariantId;
    private Integer quantity;
}
