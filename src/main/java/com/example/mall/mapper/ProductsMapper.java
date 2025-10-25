package com.example.mall.mapper;

import com.example.mall.entity.ProductVariant;
import com.example.mall.entity.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsMapper {
    Products selectProducts(Integer id);//基於id查詢商品資訊
    List<ProductVariant> selectProductsVariant(Integer productId);//查詢庫存顏色尺寸
}
