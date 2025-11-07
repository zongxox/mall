package com.example.mall.pojo.vo;

import lombok.Data;

@Data
public class CartItemsVO {
  private Integer cartItemId; // 購物車項目本身的ID (來自 cart_items)
  private Integer quantity; // 購買數量 (來自 cart_items)

  private Integer productId; // 商品ID (來自 products)
  private String productName; // 商品名稱 (來自 products)
  private String productImage; // 商品圖片 (來自 products)
  private Integer productPrice; // 商品單價 (來自 products)

  private Integer productVariantId; // 規格ID (來自 product_variants)
  private String variantColor; // 顏色 (來自 product_variants)
  private String variantSize; // 尺寸 (來自 product_variants)
  private Integer stock; // 規格庫存數量 (來自 product_variants)
}
