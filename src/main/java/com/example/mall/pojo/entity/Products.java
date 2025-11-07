package com.example.mall.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Products {

  private Integer id;
  private String name;
  private String description;
  private Integer price;
  private String imageUrl;
  private String imageUrl2;
  private String imageUrl3;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")  // 使用 @JsonFormat 來格式化返回的日期
  private java.sql.Timestamp createdTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")  // 使用 @JsonFormat 來格式化返回的日期
  private java.sql.Timestamp updatedTime;

}
