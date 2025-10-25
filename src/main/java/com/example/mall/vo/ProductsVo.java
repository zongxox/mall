package com.example.mall.vo;

import com.example.mall.entity.ProductVariant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class ProductsVo {

  private String name;
  private String description;
  private Integer price;
  private String imageUrl;
  private String imageUrl2;
  private String imageUrl3;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")  // 使用 @JsonFormat 來格式化返回的日期
  private java.sql.Timestamp createdTime;
  private List<ProductVariant> variants;
}
