package com.example.mall.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductVariantMapper {
    int delStock(Integer id,Integer quantity);//結帳後刪除庫存
}
