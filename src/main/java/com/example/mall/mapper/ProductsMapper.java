package com.example.mall.mapper;

import com.example.mall.pojo.entity.ProductVariant;
import com.example.mall.pojo.entity.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<Products> selectProductsAll(Products products);//動態查詢全部商品
    Products selectProducts(Integer id);//基於id查詢商品資訊
    List<ProductVariant> selectProductsVariant(Integer productId);//查詢庫存顏色尺寸

}
