package com.example.mall.service;

import com.example.mall.entity.ProductVariant;
import com.example.mall.entity.Products;
import com.example.mall.response.JsonResult;

import java.util.List;

public interface ProductsService {
    JsonResult selectProductsAll(Products products);//動態查詢全部商品
    JsonResult selectProducts(Integer productId);//基於id查詢商品資訊

}
